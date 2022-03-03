/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.BlogProject.controllers;

import com.example.BlogProject.models.Page;
import com.example.BlogProject.service.ServiceLayer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Gage
 */
@Controller
public class StaticController {
    
    @Autowired
    ServiceLayer service;
   
    @GetMapping("/pages")
    public String index(Model model){
        List<Page> pg= service.getPages();
        model.addAttribute("pages", pg);
        return "pages";
    }
    
    @GetMapping("/{pageName}")
    public String index(@PathVariable String pageName){
        return pageName;
    }
    
    @GetMapping("/createStatic")
    public String createStatic(Model model){
        
        return "createStatic";
    }
}
