package com.example.cafe.repository;

import com.example.cafe.entity.Order;
import com.example.cafe.entity.enums.OrderEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    Page<Order> findAllByDeletedOrderByUpdatedAtDesc(boolean b, Pageable of);

    List<Order> findAllByTableIdAndStatusIsNot(UUID tableId, OrderEnum status);
}
