package com.example.bookshop.service.impl;

import com.example.bookshop.dto.cartitem.CartItemDto;
import com.example.bookshop.dto.cartitem.CreateCartItemRequestDto;
import com.example.bookshop.dto.shoppingcart.ShoppingCartDto;
import com.example.bookshop.exception.EntityNotFoundException;
import com.example.bookshop.mapper.CartItemMapper;
import com.example.bookshop.mapper.ShoppingCartMapper;
import com.example.bookshop.model.Book;
import com.example.bookshop.model.CartItem;
import com.example.bookshop.model.ShoppingCart;
import com.example.bookshop.model.User;
import com.example.bookshop.repository.book.BookRepository;
import com.example.bookshop.repository.cartitem.CartItemRepository;
import com.example.bookshop.repository.shoppingcart.ShoppingCartRepository;
import com.example.bookshop.repository.user.UserRepository;
import com.example.bookshop.service.ShoppingCartService;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartMapper shoppingCartMapper;
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;
    private final BookRepository bookRepository;

    @Override
    public void addItem(CreateCartItemRequestDto createCartItemRequestDto) {
        ShoppingCart shoppingCart = getShoppingCartForUser();
        Book book = bookRepository.findById(createCartItemRequestDto.bookId())
                .orElseThrow(() -> new EntityNotFoundException("Can't find book with this id:"
                + createCartItemRequestDto.bookId()));
        CartItem cartItem = cartItemMapper.toCartItem(createCartItemRequestDto);
        cartItem.setBook(book);
        cartItem.setShoppingCart(shoppingCart);
        shoppingCart.getCartItems().add(cartItemRepository.save(cartItem));
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCartDto getShoppingCart() {
        User user = getUserFromContext();
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("Can't find shopping cart"
                + " for user with email:" + user.getEmail()));
        Set<CartItemDto> cartItemDtoSet = shoppingCart.getCartItems()
                .stream().map(cartItemMapper::toDto)
                .collect(Collectors.toSet());
        ShoppingCartDto shoppingCartDto = shoppingCartMapper.toDto(shoppingCart);
        shoppingCartDto.setCartItems(cartItemDtoSet);
        return shoppingCartDto;
    }

    private ShoppingCart getShoppingCartForUser() {
        User user = getUserFromContext();
        System.out.println("id: " + user.getId());
        if (shoppingCartRepository.existsShoppingCartByUser(user)) {
            return shoppingCartRepository.findShoppingCartByUser(user)
                    .orElseThrow(() -> new EntityNotFoundException("Can't find the shopping cart"));
        }
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        return shoppingCartRepository.save(shoppingCart);
    }

    private User getUserFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userRepository.findUserByEmail(userDetails.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("Can't find user with this email:"
                + userDetails.getUsername()));
    }
}
