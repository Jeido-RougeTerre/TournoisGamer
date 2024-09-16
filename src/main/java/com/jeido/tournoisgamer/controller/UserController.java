package com.jeido.tournoisgamer.controller;

import com.jeido.tournoisgamer.service.AuthService;
import com.jeido.tournoisgamer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    @Autowired
    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @RequestMapping("/user")
    public String home(Model model) {
        if(authService.isLogged()) {
            model.addAttribute("user", authService.getUser());
        }
        
        model.addAttribute("users", userService.findAll());
        
        return "user/list";
    }

    @RequestMapping("/user/{id}")
    public String detail(@PathVariable("id") UUID id, Model model){

        model.addAttribute("user", userService.findById(id));
        return "user/account";
    }

    @RequestMapping("/account")
    public String account(Model model){
        if(!authService.isLogged()) {
            return "redirect:/login";
        }

        model.addAttribute("user", authService.getUser());
        return "user/account";
    }



}
