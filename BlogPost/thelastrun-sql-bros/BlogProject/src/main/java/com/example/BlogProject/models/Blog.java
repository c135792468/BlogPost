/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.BlogProject.models;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 *
 * @author Gage
 */
@Entity
public class Blog {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int blogId;
    
    @Column(nullable = false)
    private String blogName;
    
    @Column(nullable = false)
    private String blogContent; 
    
    @Column
    private LocalDateTime blogCreationTime; 
    
    @Column
    private boolean blogPublic; 
    
    @Column
    private boolean blogApproved;
    
    @ManyToOne
    @JoinColumn(name="authorId", nullable=false)
    private Author author;
    
    @ManyToMany(mappedBy = "blog")
    private List<Image> image;
    
    @ManyToMany(mappedBy = "blog")
    private List<Tag> tag;

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }

    public void setTag(List<Tag> tag) {
        this.tag = tag;
    }

    public int getBlogId() {
        return blogId;
    }

    public List<Image> getImage() {
        return image;
    }

    public List<Tag> getTag() {
        return tag;
    }
    
    public int getId() {
        return blogId;
    }

    public void setId(int id) {
        this.blogId = id;
    }

    public String getBlogName() {
        return blogName;
    }

    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }

    public String getBlogContent() {
        return blogContent;
    }

    public void setBlogContent(String blogContent) {
        this.blogContent = blogContent;
    }

    public LocalDateTime getBlogCreationTime() {
        return blogCreationTime;
    }

    public void setBlogCreationTime(LocalDateTime blogCreationTime) {
        this.blogCreationTime = blogCreationTime;
    }

    public boolean isBlogPublic() {
        return blogPublic;
    }

    public void setBlogPublic(boolean blogPublic) {
        this.blogPublic = blogPublic;
    }

    public boolean isBlogApproved() {
        return blogApproved;
    }

    public void setBlogApproved(boolean blogApproved) {
        this.blogApproved = blogApproved;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author authorId) {
        this.author = authorId;
    }
}
