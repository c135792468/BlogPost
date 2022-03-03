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
import java.util.List;

/**
 * 
 * @author Gage
 */
public interface ServiceLayer {
    public List<Blog> getBlogs();
    public List<Blog> getUnfilteredBlogs();
    public List<Blog> getUnapprovedBlogs();
    public List<Author> getAuthors();
    public List<Tag> getTags();
    public List<Image> getImages();
    public List<Timer> getTimers();
    public List<Page> getPages();
    public Tag getTag(int id);
    public Tag getTagName(String name);
    public Blog getBlog(int id);
    public Author getAuthorEmail(String email);
    public void saveBlog(Blog blog);
    public void saveAuthor(Author author);
    public void saveTag(Tag tag);
    public void saveImage(Image image);
    public void saveTimer(Timer timer);
    public void savePage(Page page);
    public void saveSafeTimer(Timer timer, String start, String end);
    public void deleteBlog(int blogId);
}
