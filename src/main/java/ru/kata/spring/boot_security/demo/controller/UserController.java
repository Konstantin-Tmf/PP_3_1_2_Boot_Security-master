package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import java.security.Principal;


@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, UserDetailsService userDetailsService, UserRepository userRepository) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;

        this.userRepository = userRepository;
    }


    @GetMapping()
    public String info(Model model, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        model.addAttribute("user", user);
        return "user/user_information";
    }

}