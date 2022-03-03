/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.BlogProject.controllers;

import com.example.BlogProject.models.Author;
import com.example.BlogProject.models.Blog;
import com.example.BlogProject.models.Tag;
import com.example.BlogProject.security.CustomUserDetails;
import com.example.BlogProject.service.ServiceLayer;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/** Blog Controller
 *
 * @author Gage
 */
@Controller
public class BlogsController {
    
    @Autowired
    ServiceLayer service;
       
    @GetMapping("/")
    public String index(Model model){
        List<Blog> blist = service.getBlogs();

        model.addAttribute("blogs", blist);
        model.addAttribute("tags", service.getTags());
        
        Author author = service.getAuthorEmail("admin@admin.com");
        if(author == null) {
            Author admin = new Author();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode("123456");

            admin.setAuthorEmail("admin@admin.com");
            admin.setAuthorAdmin(true);
            admin.setAuthorName("admin");
            admin.setAuthorPassword(encodedPassword);
            service.saveAuthor(author);
        }
        
        return "blogs";
    }
    
    @GetMapping("/createBlog")
    public String createBlog(Model model){
        return "createBlog";
    }
    
    @PostMapping("/createBlog")
    public String addBlog(Blog blog) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof CustomUserDetails) {
            username = ((CustomUserDetails)principal).getUsername();
        } else {
             username = principal.toString();
        }
        Author author = service.getAuthorEmail(username);
        
        blog.setAuthor(author);
        blog.setBlogCreationTime(LocalDateTime.now());
        
        service.saveBlog(blog);
        return "redirect:/";
    }
    
    @GetMapping("/viewBlog")
    public String viewBlog(Integer id, Model model){
        Blog blog = service.getBlog(id);
        model.addAttribute("blog", blog);
        
        return "viewBlog";
    }
    
    @GetMapping("/viewBlogByTag")
    public String viewBlogByTag(Integer id, Model model){
        Tag tag = service.getTag(id);
        model.addAttribute("tag", tag);
        
        return "viewBlogByTag";
    }
}
