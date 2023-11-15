package com.storeace.controller;

import com.storeace.controller.main.Attributes;
import com.storeace.model.Ordering;
import com.storeace.model.StatProduct;
import com.storeace.model.enums.OrderingStatus;
import com.storeace.model.enums.ProductStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/stat")
public class StatCont extends Attributes {

    @GetMapping
    public String Stat(Model model) {
        AddAttributes(model);
        AddAttributesEnums(model);
        List<Integer> status = new ArrayList<>();
        int[] price = new int[OrderingStatus.values().length];
        List<OrderingStatus> list1 = List.of(OrderingStatus.values());
        for (int i = 0; i < list1.size(); i++) {
            int temp = orderingService.countByOrderingStatus(list1.get(i));
            status.add(temp);
            status.set(0, status.get(0) + temp);
            List<Ordering> orderingList = orderingService.findAllByOrderingStatus(list1.get(i));
            for (Ordering j : orderingList) {
                price[0] += j.getFullPrice();
                price[i] += j.getFullPrice();
            }
        }
        model.addAttribute("orderingStatus", status);
        model.addAttribute("orderingPrice", price);

        int[] product = new int[ProductStatus.values().length];
        List<ProductStatus> list2 = List.of(ProductStatus.values());
        for (int i = 0; i < list2.size(); i++) {
            List<StatProduct> productsList = statProductService.findAllByProductStatus(list2.get(i));
            for (StatProduct j : productsList) {
                product[0] += j.getQuantity();
                product[i] += j.getQuantity();
            }
        }
        model.addAttribute("productStatus", product);
        return "stat";
    }
}
