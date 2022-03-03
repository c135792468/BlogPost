/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.BlogProject.models;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author ggman
 */
@Entity
public class Timer {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int timerId;
    
    @ManyToOne
    @JoinColumn(name="blog_id", nullable=false)
    private Blog blog;
    
    @Column(nullable=false)
    private LocalDateTime blogStart;
    
    @Column(nullable=false)
    private LocalDateTime blogExpire;

    public int getTimerId() {
        return timerId;
    }

    public void setTimerId(int timerId) {
        this.timerId = timerId;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public LocalDateTime getBlogStart() {
        return blogStart;
    }

    public void setBlogStart(LocalDateTime blogStart) {
        this.blogStart = blogStart;
    }

    public LocalDateTime getBlogExpire() {
        return blogExpire;
    }

    public void setBlogExpire(LocalDateTime blogExpire) {
        this.blogExpire = blogExpire;
    }
}
