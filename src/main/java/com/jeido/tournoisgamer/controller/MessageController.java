package com.jeido.tournoisgamer.controller;

import com.jeido.tournoisgamer.entity.Message;
import com.jeido.tournoisgamer.repository.MessageRepository;
import com.jeido.tournoisgamer.repository.UserRepository;
import com.jeido.tournoisgamer.service.AuthService;
import com.jeido.tournoisgamer.service.MessageService;
import com.jeido.tournoisgamer.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
public class MessageController {

    private final UserService userService;
    private final AuthService authService;
    private final MessageService messageService;

    @Autowired
    public MessageController(UserService userService, AuthService authService, MessageService messageService) {

        this.userService = userService;
        this.authService = authService;
        this.messageService = messageService;
    }

    @GetMapping("/messages")
    public String messages(Model model) {
        if (!authService.isLogged()) {
            return "redirect:/login";
        }
        model.addAttribute("messages", messageService.findAll());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("user", authService.getUser());
        model.addAttribute("message", Message.builder().content(""));
        return "messages";
    }

    @PostMapping("/messages")
    public String messages(@Valid @ModelAttribute("message")Message message, BindingResult bindingResult, Model model) {
        if(!authService.isLogged()) {
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("messages", messageService.findAll());
            model.addAttribute("users", userService.findAll());
            model.addAttribute("user", authService.getUser());
            model.addAttribute("message", Message.builder().content(""));
            return "messages";
        }

        message.setDateTime(LocalDateTime.now());
        message.setUser(authService.getUser());
        messageService.save(message);

        return "redirect:/messages";
    }
}
