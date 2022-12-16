package com.storeace.repo;

import com.storeace.model.Cycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CycleRepo extends JpaRepository<Cycle, Long> {
    Cycle findCycleById(Long id);
    List<Cycle> findAllByReservedIsNotNull();
    List<Cycle> findAllByShipmentIsNotNull();
}
