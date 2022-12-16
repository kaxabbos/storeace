package com.storeace.service;

import com.storeace.model.Cycle;
import com.storeace.repo.CycleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CycleService {

    private final CycleRepo cycleRepo;

    @Autowired
    public CycleService(CycleRepo cycleRepo) {
        this.cycleRepo = cycleRepo;
    }

    public void add(Cycle cycle) {
        cycleRepo.save(cycle);
    }

    public void update(Cycle cycle) {
        cycleRepo.save(cycle);
    }

    public void delete(Long id) {
        cycleRepo.deleteById(id);
    }

    public void delete(Cycle cycle) {
        cycleRepo.delete(cycle);
    }

    public Cycle find(Long id) {
        return cycleRepo.findCycleById(id);
    }

    public List<Cycle> findAll() {
        return cycleRepo.findAll();
    }

    public List<Cycle> findAllByReservedIsNotNull() {
        return cycleRepo.findAllByReservedIsNotNull();
    }

    public List<Cycle> findAllByShipmentIsNotNull() {
        return cycleRepo.findAllByShipmentIsNotNull();
    }

    public Cycle addAndFlush(Cycle cycle) {
        return cycleRepo.saveAndFlush(cycle);
    }
}


