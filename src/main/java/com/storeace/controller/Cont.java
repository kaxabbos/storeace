package com.storeace.controller;

import com.storeace.controller.main.Attributes;
import com.storeace.model.Ordering;
import com.storeace.model.StatProduct;
import com.storeace.model.enums.OrderingStatus;
import com.storeace.model.enums.ProductStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Cont extends Attributes {

    @GetMapping("/stat/status")
    public List<Integer> GetStatusList() {
        List<Integer> status = new ArrayList<>();
        List<OrderingStatus> list1 = List.of(OrderingStatus.values());
        for (OrderingStatus orderingStatus : list1) {
            int temp = orderingService.countByOrderingStatus(orderingStatus);
            status.add(temp);
            status.set(0, status.get(0) + temp);
        }
        return status;
    }

    @GetMapping("/stat/price")
    public int[] GetPriceMass() {
        int[] price = new int[OrderingStatus.values().length];
        List<OrderingStatus> list1 = List.of(OrderingStatus.values());
        for (int i = 0; i < list1.size(); i++) {
            List<Ordering> orderingList = orderingService.findAllByOrderingStatus(list1.get(i));
            for (Ordering j : orderingList) {
                price[0] += j.getFullPrice();
                price[i] += j.getFullPrice();
            }
        }
        return price;
    }

    @GetMapping("/stat/product")
    public int[] GetProductMass() {
        int[] product = new int[ProductStatus.values().length];
        List<ProductStatus> list2 = List.of(ProductStatus.values());
        for (int i = 0; i < list2.size(); i++) {
            List<StatProduct> productsList = statProductService.findAllByProductStatus(list2.get(i));
            for (StatProduct j : productsList) {
                product[0] += j.getQuantity();
                product[i] += j.getQuantity();
            }
        }
        return product;
    }
}
