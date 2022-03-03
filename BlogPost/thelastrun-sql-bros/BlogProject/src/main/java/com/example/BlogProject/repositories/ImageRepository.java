/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.BlogProject.repositories;

import com.example.BlogProject.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author 69591
 */
public interface ImageRepository extends JpaRepository<Image, Integer>{
    
}
