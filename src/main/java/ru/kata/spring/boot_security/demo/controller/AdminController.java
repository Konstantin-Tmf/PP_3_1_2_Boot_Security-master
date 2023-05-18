package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.entities.User;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final UserDetailsService userDetailsService;

    @Autowired
    public AdminController(UserService userService, UserDetailsService userDetailsService) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping()
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/users";

    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "admin/new";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String editUser(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.findById(id));
        return "admin/edit";
    }

    @PatchMapping("/edit/{id}")
    public String editUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

}

