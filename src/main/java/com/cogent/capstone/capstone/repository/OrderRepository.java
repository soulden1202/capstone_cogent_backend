package com.cogent.capstone.capstone.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cogent.capstone.capstone.entities.Order;
import com.cogent.capstone.capstone.res.SaleReportResponse;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "SELECT * FROM orders where user_id = ?", nativeQuery = true)
    List<Order> findByUserId(long id);

    @Query(value = "SELECT DATE_FORMAT(created_at, '%Y-%m') AS month, SUM(total) AS total_by_month " +
            "FROM orders " +
            "GROUP BY DATE_FORMAT(created_at, '%Y-%m') " +
            "ORDER BY month", nativeQuery = true)
    List<Object[]> findTotalByMonth();
}
