package com.jeido.tournoisgamer.controller;

import com.jeido.tournoisgamer.entity.User;
import com.jeido.tournoisgamer.service.AuthService;
import com.jeido.tournoisgamer.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Controller
public class AuthController {

    private static final String LOCATION = "src/main/resources/static/image";
    private final UserService userService;
    private final AuthService authService;

    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @RequestMapping("/registration")
    public String inscription(Model model) {
        if(authService.isLogged()) {
            return "redirect:/";
        }
        model.addAttribute("user", new User());
        model.addAttribute("mode", "registration");
        return "/login/form";
    }

    @PostMapping("/registration")
    public String inscriptionForm(@Valid @ModelAttribute("user") User user,
                                  BindingResult bindingResult,
                                  @RequestParam("avatar") MultipartFile image) throws IOException {
        if (bindingResult.hasErrors()) {
            return "/login/form";
        } else {
            Path destination = Paths.get(LOCATION).resolve(Objects.requireNonNull(image.getOriginalFilename())).toAbsolutePath();
            user.setAvatar(image.getOriginalFilename());
            InputStream inputStream = image.getInputStream();
            Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);
            authService.login(user.getName(), user.getPassword());
            userService.save(user);
        }
        return "redirect:/";
    }

    @RequestMapping("/login")
    public String connexion(Model model) {
        if(authService.isLogged()) {
            return "redirect:/";
        }
        model.addAttribute("user", new User());
        model.addAttribute("mode", "login");
        return "/login/form";
    }

    @PostMapping("/login")
    public String connexionForm(@ModelAttribute("username") String username, @ModelAttribute("password") String
            password) {
        boolean connected = authService.login(username, password);
        if (connected) {
            return "redirect:/";
        }
        return "redirect:/login";
    }

    @RequestMapping("/logout")
    public String logout() {
        authService.logout();
        return "redirect:/";
    }
}
