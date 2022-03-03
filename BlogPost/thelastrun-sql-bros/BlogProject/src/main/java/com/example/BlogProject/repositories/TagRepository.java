/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.BlogProject.repositories;

import com.example.BlogProject.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author 69591
 */
public interface TagRepository extends JpaRepository<Tag, Integer>{
    @Query(value = "SELECT * FROM tag WHERE tag_name = ?1", nativeQuery = true)
    public Tag getTagByName(String tagName);
    
    @Query(value = "DELETE * FROM blog_tag WHERE blog_id = ?1", nativeQuery = true)
    public Tag deleteByBlogId(int id);
}
