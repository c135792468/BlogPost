/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.BlogProject.repositories;

import com.example.BlogProject.models.Blog;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/** Getting Blog Info
 *
 * @author ggman
 */
@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer>{
    @Query(value = "SELECT * FROM blog WHERE blog_approved = true ORDER BY blog_creation_time DESC", nativeQuery = true)
    public List <Blog> findByApproved();
    
    @Query(value = "SELECT * FROM blog WHERE blog_approved = false ORDER BY blog_creation_time DESC", nativeQuery = true)
    public List <Blog> findByUnApprove();
    
    
}
