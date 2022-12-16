package com.storeace.controller;

import com.storeace.controller.main.Attributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shipped")
public class ShippedCont extends Attributes {

    @GetMapping
    public String shipped(Model model) {
        AddAttributesShipped(model);
        return "shipped";
    }

    @GetMapping("/{idOrder}/delete")
    public String OrderDeleteShipped(@PathVariable Long idOrder) {
        orderingService.delete(idOrder);
        return "redirect:/shipped";
    }
}
