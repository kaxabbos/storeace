package com.storeace.controller;

import com.storeace.controller.main.Attributes;
import com.storeace.model.Ordering;
import com.storeace.model.enums.OrderingStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/waiting")
public class WaitingCont extends Attributes {

    @GetMapping
    public String waiting(Model model) {
        AddAttributesWaiting(model);
        return "waiting";
    }

    @GetMapping("/{idOrder}/not_reserved")
    public String OrderNotArrange1(@PathVariable Long idOrder) {
        Ordering ordering = orderingService.find(idOrder);
        ordering.setOrderingStatus(OrderingStatus.NOT_RESERVED);
        orderingService.update(ordering);
        return "redirect:/waiting";
    }

    @GetMapping("/{idOrder}/delete")
    public String OrderDeleteWaiting(@PathVariable Long idOrder) {
        orderingService.delete(idOrder);
        return "redirect:/waiting";
    }
}
