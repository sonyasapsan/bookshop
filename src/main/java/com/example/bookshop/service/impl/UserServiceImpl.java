package com.example.bookshop.service.impl;

import com.example.bookshop.dto.user.UserRegistrationRequestDto;
import com.example.bookshop.dto.user.UserRegistrationResponseDto;
import com.example.bookshop.exception.EntityNotFoundException;
import com.example.bookshop.exception.RegistrationException;
import com.example.bookshop.mapper.UserMapper;
import com.example.bookshop.model.Role;
import com.example.bookshop.model.User;
import com.example.bookshop.repository.role.RoleRepository;
import com.example.bookshop.repository.user.UserRepository;
import com.example.bookshop.service.UserService;
import java.util.Set;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private Role role;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           UserMapper userMapper, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserRegistrationResponseDto register(UserRegistrationRequestDto request)
            throws RegistrationException {
        if (userRepository.findUserByEmail(request.email()).isPresent()) {
            throw new RegistrationException("Unable to complete registration");
        }
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        role = roleRepository.findRoleByName(Role.RoleName.ROLE_USER)
                .orElseThrow(() -> new EntityNotFoundException("Can't find"
                        + " the role for your registration"));
        user.setRoles(Set.of(role));
        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }
}