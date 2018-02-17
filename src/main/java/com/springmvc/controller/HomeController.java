package com.springmvc.controller;


import com.springmvc.entity.User;
import com.springmvc.repositories.UserRepository;
import com.springmvc.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    CustomUserDetailsService userDetailsService;

    @RequestMapping(value = "/")
    public String home(){
        return "home";
    }
    
    @RequestMapping("/getCurrentUserName")
    @ResponseBody
    public String getCurrentUserName(){
       UserDetails user= userDetailsService.getCurrentUser();
       return "Hey "+user.getUsername();
    }
}
