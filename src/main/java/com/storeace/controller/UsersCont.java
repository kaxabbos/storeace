package com.storeace.controller;

import com.storeace.controller.main.Attributes;
import com.storeace.model.Users;
import com.storeace.model.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/userAll")
public class UsersCont extends Attributes {

    @GetMapping
    public String Profiles(Model model) {
        AddAttributesProfiles(model);
        return "users";
    }

    @PostMapping("/{id}/edit")
    public String ProfilesEdit(@PathVariable long id, @RequestParam Role role) {
        Users user = usersService.find(id);
        user.setRole(role);
        usersService.update(user);
        return "redirect:/userAll";
    }

    @GetMapping("/{id}/delete")
    public String ProfilesDelete(Model model, @PathVariable long id) {
        if (getUser().getId() == id) {
            AddAttributesProfiles(model);
            model.addAttribute("message", "Вы не можете удалить свой профиль");
            return "users";
        }

        usersService.delete(id);

        return "redirect:/userAll";
    }
}
