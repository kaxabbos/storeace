package com.storeace.repo;

import com.storeace.model.OrderingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderingDetailRepo extends JpaRepository<OrderingDetail, Long> {
    OrderingDetail findOrderingDetailById(Long id);
}
