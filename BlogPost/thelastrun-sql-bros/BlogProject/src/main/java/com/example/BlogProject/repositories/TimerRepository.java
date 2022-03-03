/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.BlogProject.repositories;

import com.example.BlogProject.models.Timer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ggman
 */
@Repository
public interface TimerRepository extends JpaRepository<Timer, Integer>{
    
    @Query(value = "SELECT * FROM timer WHERE blog_id = ?1", nativeQuery = true)
    public Timer findByBlogId(int id);
}
