package com.jeido.tournoisgamer.service;

import com.jeido.tournoisgamer.entity.User;
import com.jeido.tournoisgamer.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserService userService;

    @Autowired
    private HttpSession httpSession;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public boolean login(String username, String password){
        User user = userService.findByUsername(username);

        if (user == null) return false;

        if(user.getPassword().equals(password)){
            httpSession.setAttribute("username", user.getUsername());
            httpSession.setAttribute("login", "Ok");
            return true;
        }
        return false;
    }

    public boolean isLogged(){
        try{
            String isLogged = httpSession.getAttribute("login").toString();
            return isLogged.equals("Ok");
        } catch (Exception ex){
            return false;
        }
    }

    public User getUser(){
        return userService.findByUsername(httpSession.getAttribute("username").toString());
    }

    public void logout(){
        httpSession.invalidate();
    }
}
