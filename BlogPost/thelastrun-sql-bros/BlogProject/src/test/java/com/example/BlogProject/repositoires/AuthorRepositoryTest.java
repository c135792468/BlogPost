package com.example.BlogProject.repositoires;

import com.example.BlogProject.models.Author;
import com.example.BlogProject.repositories.AuthorRepository;
import static org.assertj.core.api.Java6Assertions.assertThat;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

/**
 *
 * @author cole
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class AuthorRepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private AuthorRepository repo;
    
    public AuthorRepositoryTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
        
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @org.junit.jupiter.api.Test
    public void testCreateUser() {
        Author user = new Author();
        user.setAuthorEmail("yo@gmail.com");
        user.setAuthorPassword("1234");
        user.setAuthorName("Gage Gabaldon");
     
        Author savedUser = repo.save(user);
     
        Author existUser = entityManager.find(Author.class, savedUser.getAuthorId());
     
        assertThat(user.getAuthorEmail()).isEqualTo(existUser.getAuthorEmail());
    }
    
}
