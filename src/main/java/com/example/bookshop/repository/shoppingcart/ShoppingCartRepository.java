package com.example.bookshop.repository.shoppingcart;

import com.example.bookshop.model.ShoppingCart;
import com.example.bookshop.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    boolean existsShoppingCartByUser(User user);

    Optional<ShoppingCart> findShoppingCartByUser(User user);
}
