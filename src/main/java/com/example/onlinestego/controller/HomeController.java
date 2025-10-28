package com.example.onlinestego.controller;

import com.example.onlinestego.model.User;
import com.example.onlinestego.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/", "/home"})
    public String home(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) return "redirect:/login";

        List<User> users = userService.findAll();
        User me = userService.findByUsername(username).orElse(null);

        model.addAttribute("users", users);
        model.addAttribute("me", me);
        return "home";
    }
}
