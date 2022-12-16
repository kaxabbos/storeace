package com.storeace.controller;

import com.storeace.controller.main.Attributes;
import com.storeace.model.User;
import com.storeace.model.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/reg")
public class RegCont extends Attributes {

    @GetMapping
    public String reg() {
        return "reg";
    }

    @PostMapping
    public String regUser(Model model, @RequestParam String username, @RequestParam String fio, @RequestParam String password, @RequestParam String passwordRepeat) {
        if (userService.findAll().isEmpty() || userService.findAll().size() == 0){
            userService.add(new User(username, password, fio, Role.ADMIN, defaultAvatar));
            return "redirect:/login";
        }


        if (userService.findByUsername(username) != null) {
            model.addAttribute("message", "Пользователь с таким логином уже существует");
            AddAttributes(model);
            return "reg";
        }

        if (!password.equals(passwordRepeat)) {
            model.addAttribute("message", "Некорректный ввод паролей");
            AddAttributes(model);
            return "reg";
        }

        userService.add(new User(username, password, fio, Role.EMPLOYEE, defaultAvatar));

        return "redirect:/login";
    }
}