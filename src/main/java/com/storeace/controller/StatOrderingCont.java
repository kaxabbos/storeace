package com.storeace.controller;

import com.storeace.controller.main.Attributes;
import com.storeace.model.enums.OrderingStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/statOrdering")
public class StatOrderingCont extends Attributes {

    @GetMapping
    public String statOrder(Model model) {
        AddAttributesStatOrdering(model, OrderingStatus.ALL,null);
        return "statOrdering";
    }

    @PostMapping("/search")
    public String statOrderStatus(Model model, @RequestParam OrderingStatus orderingStatus, @RequestParam String date) {
        AddAttributesStatOrdering(model, orderingStatus, date);
        return "statOrdering";
    }

}
