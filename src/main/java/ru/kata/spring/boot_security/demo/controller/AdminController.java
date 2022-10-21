package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @RequestMapping
    public String showAllUsers(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("newUser", new User());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user",user);
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin";
    }


    @RequestMapping("/saveUser")
    public String saveUser(@ModelAttribute("newUser") User user, @RequestParam("user_role") String[] result)  {
        for (String role : result) {
            user.setRole(roleService.getRole(role));
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }


    @RequestMapping("/update/{id}")
    public String updateUser(@ModelAttribute("user") User user, Model model,
                             @RequestParam("user_role") String[] result) {
        model.addAttribute("roles", roleService.getAllRoles());
        for (String role : result) {
            user.setRole(roleService.getRole(role));
        }
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @RequestMapping("/deleteUser")
    public String deleteUserById(@RequestParam("userId") long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }
}
