package com.vodafoneziggo.ordermanager.db.repo;

import com.vodafoneziggo.ordermanager.db.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA Repository class to perform database operation for Orders
 *
 * @author Thambi Thurai Chinnadurai
 */
@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

    Optional<Orders> findByProductIdAndEmail(long productId, String email);
}
