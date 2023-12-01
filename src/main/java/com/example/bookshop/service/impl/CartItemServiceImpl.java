package com.example.bookshop.service.impl;

import com.example.bookshop.dto.cartitem.UpdateCartItemRequestDto;
import com.example.bookshop.exception.EntityNotFoundException;
import com.example.bookshop.model.CartItem;
import com.example.bookshop.repository.cartitem.CartItemRepository;
import com.example.bookshop.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;

    @Override
    public void update(UpdateCartItemRequestDto updateCartItemRequestDto, Long id) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find cart item "
                        + "with such id:" + id));
        cartItem.setQuantity(updateCartItemRequestDto.quantity());
        cartItemRepository.save(cartItem);
    }

    @Override
    public void delete(Long id) {
        cartItemRepository.deleteById(id);
    }
}
