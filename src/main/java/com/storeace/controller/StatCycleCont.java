package com.storeace.controller;

import com.storeace.controller.main.Attributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/statCycle")
public class StatCycleCont extends Attributes {
    @GetMapping
    public String statCycle(Model model) {
        AddAttributes(model);
        return "statCycle";
    }

    @GetMapping("/accepted")
    public String statCycleAccepted(Model model) {
        AddAttributesStatCycleAccepted(model);
        return "statCycleAccepted";
    }

    @GetMapping("/reserved")
    public String statCycleReserved(Model model) {
        AddAttributesStatCycleReserved(model);
        return "statCycleReserved";
    }

    @GetMapping("/shipment")
    public String statCycleShipment(Model model) {
        AddAttributesStatCycleShipment(model);
        return "statCycleShipment";
    }


}
