package com.example.BlogProject.repositoires;

import com.example.BlogProject.models.Author;
import com.example.BlogProject.models.Blog;
import com.example.BlogProject.models.Tag;
import com.example.BlogProject.repositories.AuthorRepository;
import com.example.BlogProject.repositories.BlogRepository;
import com.example.BlogProject.repositories.TagRepository;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
@Transactional
public class TagRepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BlogRepository repo;

    @Autowired
    private AuthorRepository repoAuth;
    
    @Autowired
    private TagRepository repoTag;
    
    
    public TagRepositoryTest() {
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
    public void testRemoveTag() {
        Author user = new Author();
        user.setAuthorEmail("test@gmail.com");
        user.setAuthorPassword("1234");
        user.setAuthorName("Test Author");
        
        Author savedUser = repoAuth.save(user);
        
        Blog blog = new Blog();
        blog.setBlogName("Test Blog Post");
        blog.setBlogContent("This is a test of the blog tests.");
        blog.setAuthor(user);

        
        List<Blog> blogList = new ArrayList<>();
        blogList.add(blog);
        
        Tag hash = new Tag();
        hash.setTagName("test");
        hash.setBlog(blogList);
        Tag hash2 = new Tag();
        hash.setTagName("test2");
        hash.setBlog(blogList);
        
        Tag savedTag = repoTag.save(hash);
        Tag savedTag2 = repoTag.save(hash2);
        List<Tag> tagList = new ArrayList<>();
        tagList.add(hash);
        tagList.add(hash2);
        
        blog.setTag(tagList);
        Blog savedBlog = repo.save(blog);
        
        assertTrue(savedBlog.getTag().contains(tagList.get(0)));
        assertTrue(savedBlog.getTag().contains(tagList.get(1)));
        
        tagList.remove(hash);
        blog.setTag(tagList);
        savedBlog = repo.save(blog);

        assertFalse(savedBlog.getTag().contains(hash));
        
    }
    
}
