package com.storeace.service;

import com.storeace.model.OrderingDetail;
import com.storeace.repo.OrderingDetailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderingDetailService {

    private final OrderingDetailRepo orderingDetailRepo;

    @Autowired
    public OrderingDetailService(OrderingDetailRepo orderingDetailRepo) {
        this.orderingDetailRepo = orderingDetailRepo;
    }

    public void add(OrderingDetail orderingDetail) {
        orderingDetailRepo.save(orderingDetail);
    }

    public void update(OrderingDetail orderingDetail) {
        orderingDetailRepo.save(orderingDetail);
    }

    public void delete(Long id) {
        orderingDetailRepo.deleteById(id);
    }

    public void delete(OrderingDetail orderingDetail) {
        orderingDetailRepo.delete(orderingDetail);
    }

    public OrderingDetail find(Long id) {
        return orderingDetailRepo.findOrderingDetailById(id);
    }

    public OrderingDetail addAndFlush(OrderingDetail orderingDetail) {
        return orderingDetailRepo.saveAndFlush(orderingDetail);
    }
}
