/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.BlogProject.models;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 *
 * @author 69591
 */
@Entity
public class Image {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int imageId;
    
    @Column(nullable=false)
    private String imageLocation;
    
    @ManyToMany
    @JoinTable(name = "blog_images",
            joinColumns = {@JoinColumn(name = "image_id")},
            inverseJoinColumns = {@JoinColumn(name = "blog_id")})
    private List<Blog> blog;
    
    @ManyToMany
    @JoinTable(name = "page_images",
            joinColumns = {@JoinColumn(name = "image_id")},
            inverseJoinColumns = {@JoinColumn(name = "page_id")})
    private List<Page> page;

    public List<Page> getPage() {
        return page;
    }

    public void setPage(List<Page> page) {
        this.page = page;
    }
    
    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setBlog(List<Blog> blog) {
        this.blog = blog;
    }

    public List<Blog> getBlog() {
        return blog;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public int getImageId() {
        return imageId;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public Image() {
    }
}
