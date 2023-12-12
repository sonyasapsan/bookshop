package com.example.bookshop.repository.order;

import com.example.bookshop.model.Order;
import com.example.bookshop.model.User;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> getAllByUser(User user, Pageable pageable);
}
