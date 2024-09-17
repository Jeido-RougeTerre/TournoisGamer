package com.jeido.tournoisgamer.service;

import com.jeido.tournoisgamer.entity.User;
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

    public boolean login(User user, String password){

        if (user == null) return false;

        if(user.getPassword().equals(password)){
            httpSession.setAttribute("username", user.getUsername());
            httpSession.setAttribute("isLoggedIn", true);
            return true;
        }
        return false;
    }

    public boolean isLogged(){
        try{
            return (boolean) httpSession.getAttribute("isLoggedIn");
        } catch (Exception ex){
            return false;
        }
    }

    public User getUser(){
        if (!isLogged()) return null;
        return userService.findByUsername((String) httpSession.getAttribute("username"));
    }

    public void logout(){
        httpSession.invalidate();
    }
}
