/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.BlogProject.controllers;

import com.example.BlogProject.models.Author;
import com.example.BlogProject.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


/**
 *
 * @author Gage
 */
@Controller
public class LoginController {
    
    @Autowired
    ServiceLayer service;
    
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new Author());
     
        return "signup_form";
    }
    
    @PostMapping("/process_register")
    public String processRegister(Author user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getAuthorPassword());
        user.setAuthorPassword(encodedPassword);
        
        service.saveAuthor(user);
     
        return "redirect:/admin";
    }
}
