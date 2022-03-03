/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.BlogProject.controllers;

import com.example.BlogProject.models.Author;
import com.example.BlogProject.models.Blog;
import com.example.BlogProject.models.Page;
import com.example.BlogProject.models.Tag;
import com.example.BlogProject.models.Timer;
import com.example.BlogProject.models.UserError;
import com.example.BlogProject.service.ServiceLayer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Gage
 */
@Controller
public class AdminController {
    
    @Autowired
    ServiceLayer service;
    
    List<UserError> errors = new ArrayList();
    List<UserError> success = new ArrayList();
    
    @GetMapping("/admin")
    public String admin(Model model){
        List<Blog> blog = service.getUnapprovedBlogs();
        List<Tag> tag = service.getTags();
        List<Timer> t = service.getTimers();
        
        model.addAttribute("blogs", blog);
        model.addAttribute("tags", tag);
        model.addAttribute("timers", t);
        model.addAttribute("errors", errors);
        model.addAttribute("success", success);
        
        return "admin";
    }
    @GetMapping("/users")
    public String listUsers(Model model) {
        errors.clear();
        success.clear();
        
        List<Author> listUsers = service.getAuthors();
        model.addAttribute("listUsers", listUsers);
     
        return "users";
    }

    @PostMapping("/createTag")
    public String createTag(Tag tag){
        errors.clear();
        success.clear();
        
        UserError e = new UserError();
        e.setErrorName("Success Tag");
        e.setErrorDescription("Successfully created Tag");
        
        success.add(e);
        
        service.saveTag(tag);
        return "redirect:/admin";
    }
    
    @PostMapping("/addTagToBlog")
    public String addTagToBlog(Integer tagId, Integer blogId){
        errors.clear();
        success.clear();
        
        Tag tag = service.getTag(tagId);
        Blog blog = service.getBlog(blogId);
        
        tag.getBlog().add(blog);
        
        service.saveTag(tag);
        return "redirect:/admin";
    }
   
    @PostMapping("/tag")
    public String addTagToBlog(String tagName, Model model){
        errors.clear();
        success.clear();
        
        Tag t = service.getTagName(tagName);
        if(t != null){
            model.addAttribute("tag", t);
            return "redirect:/viewBlogByTag?id=" + t.getTagId();
        }
        
        return "redirect:/";
    }
    
    @GetMapping("/createTimer")
    public String viewTimer(Integer id, Model model){
        errors.clear();
        success.clear();
        
        Blog blog = service.getBlog(id);
        model.addAttribute("blog", blog);
        return "timer";
    }
    
    @PostMapping("/createTimer")
    public String createTime(String post, String expire, Integer id){
        errors.clear();
        success.clear();

        Timer t = new Timer();
        
        Blog blog = service.getBlog(id);
        
        t.setBlog(blog);
        
        try{
            LocalDateTime start = LocalDateTime.parse(post);
            
            LocalDateTime expired = LocalDateTime.parse(expire);
            
            if(!start.isBefore(expired)){
                UserError e = new UserError();
                e.setErrorName("Timer Error");
                e.setErrorDescription("Begin time set before end time");   
                errors.add(e);
                
                return "redirect:/admin";
            }
            t.setBlogStart(start);
            
            t.setBlogExpire(expired);
            
            service.saveTimer(t);
            
        } catch( DateTimeParseException d) {
                UserError e = new UserError();
                e.setErrorName("Timer Error");
                e.setErrorDescription("Timer datetime incorrect format or time");   
                
                errors.add(e);
        }
        
        UserError e = new UserError();
        e.setErrorName("Success Timer");
        e.setErrorDescription("Timer has been set for " + blog.getBlogName());   
        
        success.add(e);
        
        return "redirect:/admin";
    }
    
    @GetMapping("/approveBlog")
    public String approveBlog(Integer id, Model model){
        errors.clear();
        success.clear();
        
        Blog blog = service.getBlog(id);
        
        UserError e = new UserError();
        e.setErrorName("Success Approve");
        e.setErrorDescription("Approved blog: " + blog.getBlogName());   
        
        success.add(e);
        
        blog.setBlogApproved(true);
        
        service.saveBlog(blog);
        return "redirect:/admin";
    }
    
    @GetMapping("/deleteBlog")
    public String deleteBlog(Integer id, Model model){
        errors.clear();
        success.clear();
        
        service.deleteBlog(id);
        
        UserError e = new UserError();
        
        e.setErrorName("Success Delete");
        e.setErrorDescription("Deleted a Blog");
        
        success.add(e);
        
        return "redirect:/admin";
    }
    
    @PostMapping("/createStatic")
    public String addStatic(String pageName, String pageContent){
        errors.clear();
        success.clear();
        
        Page page = new Page();
        page.setPageName(pageName);
        page.setPageLocation("/" + pageName);
        
        try {
            addStaticPage(pageName, pageContent);
            UserError e = new UserError();
            e.setErrorName("Page Successful");
            e.setErrorDescription("Success Creating Page: " + page.getPageName()); 
            
            success.add(e);
            service.savePage(page);
            
        } catch (IOException ex) {
            UserError e = new UserError();
            e.setErrorName("Page Creation Error");
            e.setErrorDescription("Error creating page");  
            
            errors.add(e);
            
            return "redirect:/admin";
        }
        
        return "redirect:/admin";
    }
    
     /** Creates a Template HTML page
     * 
     * @param pageName
     * @param pageContent
     * @throws IOException 
     */
    public void addStaticPage(String pageName, String pageContent) throws IOException{
        File f = new File("src/main/resources/templates/" + pageName + ".html");
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        // write the header
        bw.write("<!doctype html>\n"
                + "<html lang=\"en\" xmlns:th=\"http://www.thymeleaf.org\">\n"
                + "<head>\n"
                + "<!-- Required meta tags -->\n"
                + "<meta charset=\"utf-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n"
                + "<!-- Bootstrap CSS -->\n"
                + "<link rel=\"icon\" href=\"data:;base64,iVBORw0KGgo=\">\n"
                + "<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css\" integrity=\"sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO\" crossorigin=\"anonymous\">\n"
                + "<link rel=\"stylesheet\" href=\"https://use.fontawesome.com/releases/v5.2.0/css/all.css\" integrity=\"sha384-hWVjflwFxL6sNzntih27bfxkr27PmbbK/iSvJ+a4+0owXq79v+lsFkW54bOGbiDQ\" crossorigin=\"anonymous\">\n"
                + "<title>Sarah\'s Thoughts</title>\n"
                + "</head>\n\n");

        // write the beginning body 
        bw.write("<body>\n"
                + "<!--Begin main body -->\n"
                + "<!-- Navbar -->\n"
                + "<div class=\"container\">\n"
                + "<nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">\n"
                + "<div class=\"container-fluid\">\n"
                + "<a class=\"navbar-brand\" href=\"#\">Sarah\'s Thoughts </a>\n"
                + "<button class=\"navbar-toggler\" type=\"button\" data-bs-toggle=\"collapse\" data-bs-target=\"#navbarNavAltMarkup\" aria-controls=\"navbarNavAltMarkup\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\n"
                + "<span class=\"navbar-toggler-icon\"></span>\n"
                + "</button>\n"
                + "<div class=\"collapse navbar-collapse\" id=\"navbarNavAltMarkup\">\n"
                + "<div class=\"navbar-nav\">\n"
                + "<a class=\"nav-link\" th:href=\"@{/}\">Home</a>\n"
                + "<li class=\"nav-item dropdown\">\n"
                + "<a class=\"nav-link dropdown-toggle\" id=\"navbarDropdownMenuLink\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">Blogs</a>\n"
                + "<div class=\"dropdown-menu\" aria-labelledby=\"navbarDropdownMenuLink\">\n"
                + "<a class=\"dropdown-item\" th:href=\"@{/blogs}\">View Blogs</a>\n"
                + "<a class=\"dropdown-item\" th:href=\"@{/createBlog}\">Create Blog</a>\n"
                + "</div>\n"
                + "</li>\n"
                + "<li class=\"nav-item dropdown\">\n"
                + "<a class=\"nav-link dropdown-toggle\" id=\"navbarDropdownMenuLink\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">Pages</a>\n"
                + "<div class=\"dropdown-menu\" aria-labelledby=\"navbarDropdownMenuLink\">\n"
                + "<a class=\"dropdown-item\" th:href=\"@{/pages}\">View Pages</a>\n"
                + "<a class=\"dropdown-item\" th:href=\"@{/createStatic}\">Create Page</a>\n"
                + "</div>\n"
                + "</li>\n"
                + "<a class=\"nav-link\" th:href=\"@{/about}\">About</a>\n"
                + "<a class=\"nav-link\" th:href=\"@{/admin}\">Admin</a>\n"
                + "<a class=\"nav-link\" th:href=\"@{/login}\">Login</a>"
                + "<a class=\"nav-link\" th:href=\"@{/register}\">Signup</a>\n"
                + "</div>\n"
                + "</div>\n"
                + "</div>\n"
                + "</nav>\n"
                + "</div>\n"
                + "<!-- End Navbar -->\n");
        
        // page name header
        bw.write("<div class=\"container\">\n"
                + "<h2>" + pageName + "</h2>\n");
        // write the page content given by the user
        bw.write("<div>" + pageContent + "</div>\n");
        
        // close the container div and footer
        bw.write("</div>\n"
                + "<footer class=\"bg-light text-center text-black\">\n" +
                "<!-- Grid container -->\n" +
                "<div class=\"container p-4 pb-0\">\n" +
                "<p>Follow on Social!</p>\n" +
                "<!-- Section: Social media -->\n" +
                "<section class=\"mb-4\">\n" +
                "<!-- Facebook -->\n" +
                "<a class=\"btn btn-primary btn-floating m-1\" style=\"background-color: #3b5998;\" href=\"https://facebook.com\" role=\"button\"><i class=\"fab fa-facebook-f\"></i></a>\n" +
                "\n" +
                "<!-- Twitter -->\n" +
                "<a class=\"btn btn-primary btn-floating m-1\" style=\"background-color: #55acee;\" href=\"https://twitter.com\" role=\"button\"><i class=\"fab fa-twitter\"></i></a>\n" +
                "\n" +
                "<!-- Google -->\n" +
                "<!-- <a class=\"btn btn-primary btn-floating m-1\" style=\"background-color: #dd4b39;\" href=\"#!\" role=\"button\"><i class=\"fab fa-google\"></i></a> -->\n" +
                "\n" +
                "<!-- Instagram -->\n" +
                "<a class=\"btn btn-primary btn-floating m-1\" style=\"background-color: #ac2bac;\" href=\"https://instagram.com\" role=\"button\"><i class=\"fab fa-instagram\"></i></a>\n" +
                "\n" +
                "<!-- Linkedin -->\n" +
                "<a class=\"btn btn-primary btn-floating m-1\" style=\"background-color: #0082ca;\" href=\"https://linkedin.com\" role=\"button\"><i class=\"fab fa-linkedin-in\"></i></a>\n" +
                "</section>\n" +
                "<!-- Section: Social media -->\n" +
                "</div>\n" +
                "<!-- Grid container -->\n" +
                "\n" +
                "<!-- Copyright -->\n" +
                "<div class=\"container\">\n" +
                "<div class=\"text-center text-white p-3 bg-dark\" style=\"background-color: rgba(0, 0, 0, 0.2);\">\n" +
                "© 2022 Copyright: M Three Corporate Consulting Ltd trading as mthree.\n" +
                "</div>\n" +
                "</div>\n" +
                "<!-- Copyright -->\n" +
                "</footer>");
        
        // write the rest of the script tags and close body and html
        bw.write("<!-- Optional JavaScript --> \n"
                + "<!-- jQuery first, then Popper.js, then Bootstrap JS --><script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\" integrity=\"sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo\" crossorigin=\"anonymous\"></script>\n"
                + "<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js\" integrity=\"sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49\" crossorigin=\"anonymous\"></script>\n"
                + "<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js\" integrity=\"sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy\" crossorigin=\"anonymous\"></script>\n"
                + "</body>\n"
                + "</html>\n"
                + "");
        
        // finish
        bw.flush();
        bw.close();
    }
   
}
