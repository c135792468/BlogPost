/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.BlogProject.repositories;

import com.example.BlogProject.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/** Getting the User Info
 *
 * @author Gage
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer>{
    @Query(value = "SELECT * FROM Author WHERE author_email = ?1", nativeQuery = true)
    public Author findByEmail(String email);
}