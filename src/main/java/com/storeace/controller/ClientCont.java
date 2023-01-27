package com.storeace.controller;

import com.storeace.controller.main.Attributes;
import com.storeace.model.Client;
import com.storeace.model.Ordering;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientCont extends Attributes {

    @GetMapping
    public String Clients(Model model) {
        AddAttributesClients(model);
        return "client";
    }

    @PostMapping("/add")
    public String ClientAdd(@RequestParam String fio, @RequestParam String tel) {
        clientService.add(new Client(fio, tel));
        return "redirect:/client";
    }

    @PostMapping("/{id}/edit")
    public String ClientEdit(@RequestParam String fio, @RequestParam String tel, @PathVariable Long id) {
        Client client = clientService.find(id);
        client.setFio(fio);
        client.setTel(tel);
        clientService.update(client);
        return "redirect:/client";
    }

    @GetMapping("/{id}/delete")
    public String ClientDelete(@PathVariable Long id) {
        Client client = clientService.find(id);
        List<Ordering> orderingList = client.getOrderings();
        orderingList.forEach(ordering -> DeleteOrderingAndOrderingDetailsAndStatProduct(ordering.getId()));
        clientService.delete(client);
        return "redirect:/client";
    }
}
