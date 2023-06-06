package com.storeace.controller;

import com.storeace.controller.main.Attributes;
import com.storeace.model.*;
import com.storeace.model.enums.OrderingStatus;
import com.storeace.model.enums.ProductStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ordering")
public class OrderingCont extends Attributes {
    @GetMapping
    public String Orders(Model model) {
        AddAttributesOrdering(model);
        return "ordering";
    }

    @GetMapping("/{idOrder}/waiting")
    public String OrderWaiting(@PathVariable Long idOrder) {
        Ordering ordering = orderingService.find(idOrder);
        ordering.setOrderingStatus(OrderingStatus.WAITING);
        orderingService.update(ordering);
        return "redirect:/ordering";
    }

    @PostMapping("/add")
    public String OrderAdd(Model model, @RequestParam Long clientId, @RequestParam String date) {
        Client client = clientService.find(clientId);

        List<Ordering> orderingList = client.getOrderings();
        for (Ordering i : orderingList) {
            if (i.getDate().equals(date)) {
                if (i.getOrderingStatus() == OrderingStatus.NOT_RESERVED) {
                    AddAttributesOrdering(model);
                    model.addAttribute("message", "Заказ для клиента \"" + client.getFio() + "\" с датой \"" + date + "\" в оформлении");
                    return "ordering";
                }
                if (i.getOrderingStatus() == OrderingStatus.WAITING) {
                    AddAttributesOrdering(model);
                    model.addAttribute("message", "Заказ для клиента \"" + client.getFio() + "\" с датой \"" + date + "\" в ожидании");
                    return "ordering";
                }
            }
        }

        Ordering ordering = orderingService.addAndFlush(new Ordering(date));
        Cycle cycle = cycleService.addAndFlush(new Cycle(ordering));
        client.addOrdering(ordering);
        clientService.update(client);
        ordering.setCycle(cycle);

        orderingService.update(ordering);

        return "redirect:/ordering";
    }

    @GetMapping("/{idOrder}/reserved")
    public String OrderArrange(Model model, @PathVariable Long idOrder) {
        Ordering ordering = orderingService.find(idOrder);

        List<OrderingDetail> orderingDetailList = ordering.getDetails();

        if (orderingDetailList.size() == 0) {
            AddAttributesOrdering(model);
            model.addAttribute("message", "Не выбраны детали заказа: " + ordering.getId() + " - " + ordering.getClient().getFio());
            return "ordering";
        }

        for (OrderingDetail i : orderingDetailList) {
            Product product = productService.find(i.getProduct().getId());
            if (i.getQuantity() > product.getQuantity()) {
                AddAttributesOrdering(model);
                model.addAttribute("message", "Недостаточно товаров для заказа: " + ordering.getId() + " - " + ordering.getClient().getFio());
                return "ordering";
            }
            if (i.getQuantity() == 0) {
                AddAttributesOrdering(model);
                model.addAttribute("message", "Некорректный выбор деталей заказа: " + ordering.getId() + " - " + ordering.getClient().getFio());
                return "ordering";
            }
        }

        for (OrderingDetail i : orderingDetailList) {
            Product product = productService.find(i.getProduct().getId());
            product.setQuantity(product.getQuantity() - i.getQuantity());
            product.addStat(statProductService.addAndFlush(new StatProduct(i.getQuantity(), DateNow(), i.getId(), ProductStatus.RESERVED)));
            productService.update(product);
        }

        ordering.setOrderingStatus(OrderingStatus.RESERVED);
        ordering.getCycle().CalculationAcceptedTime();
        orderingService.update(ordering);
        return "redirect:/ordering";
    }

    @PostMapping("/{idOrdering}/edit")
    public String OrderEdit(Model model, @RequestParam String date, @PathVariable Long idOrdering) {
        Ordering ordering = orderingService.find(idOrdering);

        Client client = ordering.getClient();
        List<Ordering> orderingList = client.getOrderings();
        for (Ordering i : orderingList) {
            if (i.getId().equals(idOrdering)) continue;
            if (i.getDate().equals(date) && (i.getOrderingStatus() == OrderingStatus.NOT_RESERVED || i.getOrderingStatus() == OrderingStatus.WAITING)) {
                AddAttributesOrdering(model);
                if (i.getOrderingStatus() == OrderingStatus.NOT_RESERVED) {
                    model.addAttribute("message", "Заказ для клиента \"" + client.getFio() + "\" с датой \"" + date + "\" в оформлении");
                } else {
                    model.addAttribute("message", "Заказ для клиента \"" + client.getFio() + "\" с датой \"" + date + "\" в ожидании");
                }
                return "ordering";
            }
        }

        ordering.setDate(date);
        orderingService.update(ordering);
        return "redirect:/ordering";
    }

    @GetMapping("/{idOrder}/delete")
    public String OrderDelete(@PathVariable Long idOrder) {
        Ordering ordering = orderingService.find(idOrder);
        Client client = ordering.getClient();
        client.removeOrdering(ordering);
        clientService.update(client);
        return "redirect:/ordering";
    }
}
