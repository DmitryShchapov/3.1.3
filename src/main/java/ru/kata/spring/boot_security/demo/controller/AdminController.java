package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    private RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @RequestMapping
    public String showAllUsers(Model model) {
        List<User> allUsers = userService.getAllUsers();
        model.addAttribute("allUsers", allUsers);
        return "all-users";
    }

    @RequestMapping("/addUser")
    public String addUser(Model model) {

        model.addAttribute("user", new User());

        return "user-info";
    }

    @RequestMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @RequestMapping("/updateUser")
    public String updateUser(@RequestParam("userId") long id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        return "user-info";
    }

    @RequestMapping("/deleteUser")
    public String deleteUserById(@RequestParam("userId") long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }


    @PostConstruct
    public void addDefaultUser() {
        roleService.saveRole(new Role(1L, "ROLE_USER"));
        roleService.saveRole(new Role(2L, "ROLE_ADMIN"));
        Set<Role> roleSet1 = new HashSet<>();
        roleSet1.add(roleService.getRole(1L));
        Set<Role> roleSet2 = new HashSet<>();
        roleSet2.add(roleService.getRole(1L));
        roleSet2.add(roleService.getRole(2L));
        User user1 = new User("user", "user", roleSet1, "Igor", "Suvorov", 24, "123123123");
        User user2 = new User("admin", "admin", roleSet2, "Petr", "Sidorov", 27, "156756756");
        saveUser(user1);
        saveUser(user2);
    }




}
