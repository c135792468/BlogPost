/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.BlogProject.service;

import com.example.BlogProject.models.Author;
import com.example.BlogProject.models.Blog;
import com.example.BlogProject.models.Image;
import com.example.BlogProject.models.Page;
import com.example.BlogProject.models.Tag;
import com.example.BlogProject.models.Timer;
import com.example.BlogProject.repositories.AuthorRepository;
import com.example.BlogProject.repositories.BlogRepository;
import com.example.BlogProject.repositories.PageRepository;
import com.example.BlogProject.repositories.TagRepository;
import com.example.BlogProject.repositories.TimerRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Gage
 */
@Repository
public class ServiceLayerImpl implements ServiceLayer{
    
    @Autowired
    BlogRepository blogs;
    
    @Autowired
    AuthorRepository authors;
    
    @Autowired
    TagRepository tags;
    
    @Autowired
    TimerRepository timers;
    
    @Autowired
    PageRepository pages;

    @Override
    public List<Blog> getBlogs() {
        List<Blog> blist = blogs.findByApproved();
        List<Timer> tlist = timers.findAll();
        
        for(Timer t: tlist){
            if(!LocalDateTime.now().isAfter(t.getBlogStart())){
                blist.remove(t.getBlog());
            }
            if(LocalDateTime.now().isAfter(t.getBlogExpire())){
                blist.remove(t.getBlog());
            }
        }
        
        return blist;
    }

    @Override
    public List<Author> getAuthors() {
        return authors.findAll();
    }

    @Override
    public List<Tag> getTags() {
        return tags.findAll();  
    }

    @Override
    public List<Image> getImages() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Timer> getTimers() {
        return timers.findAll();
    }

    @Override
    public List<Page> getPages() {
        return pages.findAll();
    }

    @Override
    public Tag getTag(int id) {
        return tags.getById(id);
    }

    @Override
    public Tag getTagName(String name) {
        return tags.getTagByName(name);
    }

    @Override
    public Blog getBlog(int id) {
        return blogs.getById(id);
    }

    @Override
    public void saveBlog(Blog blog) {
        blogs.save(blog);
    }

    @Override
    public void saveAuthor(Author author) {
        authors.save(author);
    }

    @Override
    public void saveTag(Tag tag) {
        tags.save(tag);
    }

    @Override
    public void saveImage(Image image) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveTimer(Timer timer) {
        timers.save(timer);
    }

    @Override
    public void savePage(Page page) {
        pages.save(page);
    }

    @Override
    public void deleteBlog(int blogId) {
        Blog deleteBlog = blogs.findById(blogId).orElse(null);
        
        List<Tag> tg = tags.findAll();
        
        List<Blog> temp;
        
        for(Tag tag : tg){
            temp = tag.getBlog();
            if(temp.contains(deleteBlog)){
                temp.remove(deleteBlog);
            }
        }
        
        Timer t = timers.findByBlogId(blogId);

        if(t != null){
            timers.delete(t);
        }
        
        blogs.deleteById(blogId);
    }

    @Override
    public List<Blog> getUnfilteredBlogs() {
        return blogs.findAll();
    }

    @Override
    public Author getAuthorEmail(String email) {
        return authors.findByEmail(email);
    }

    @Override
    public List<Blog> getUnapprovedBlogs() {
        return blogs.findByUnApprove();
    }

    @Override
    public void saveSafeTimer(Timer timer, String start, String end) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
