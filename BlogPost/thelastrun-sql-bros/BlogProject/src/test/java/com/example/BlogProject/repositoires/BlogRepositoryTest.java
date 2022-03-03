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
import static org.junit.jupiter.api.Assertions.assertEquals;
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
@Transactional
@Rollback(false)
public class BlogRepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BlogRepository repo;

    @Autowired
    private AuthorRepository repoAuth;
    
    @Autowired
    private TagRepository repoTag;
    
    public BlogRepositoryTest() {
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
    public void testCreateAuthorAndBlog() {
     
        Author user = new Author();
        user.setAuthorEmail("test@gmail.com");
        user.setAuthorPassword("1234");
        user.setAuthorName("Test Author");
        Author savedUser = repoAuth.save(user);

        Blog blog = new Blog();
        blog.setBlogName("Test Blog Post");
        blog.setBlogContent("This is a test of the blog tests.");
        blog.setAuthor(user);

        Blog savedBlog = repo.save(blog);

        Blog existingBlog = entityManager.find(Blog.class, savedBlog.getBlogId());
        
        assertTrue(blog.getBlogContent().contentEquals(existingBlog.getBlogContent()));
        assertTrue(savedBlog.getBlogName().contentEquals(existingBlog.getBlogName()));
        assertTrue(existingBlog.getBlogName().equals("Test Blog Post"));
    }
    
    @org.junit.jupiter.api.Test
    public void createBlogWithAuthAndTag() {
        Author user = new Author();
        user.setAuthorEmail("test@gmail.com");
        user.setAuthorPassword("1234");
        user.setAuthorName("Test Author");
        
        Author savedUser = repoAuth.save(user);
        
        Blog blog = new Blog();
        blog.setBlogName("Test Blog Post");
        blog.setBlogContent("This is a test of the blog tests.");
        blog.setAuthor(user);

        Blog savedBlog = repo.save(blog);
        
        List<Blog> blogList = new ArrayList<>();
        blogList.add(blog);
        
        Tag hash = new Tag();
        hash.setTagName("test");
        hash.setBlog(blogList);
        
        Tag savedTag = repoTag.save(hash);
        
        assertTrue(savedTag.getTagName().contentEquals(hash.getTagName()));
        
    }
    
    @org.junit.jupiter.api.Test
    public void testTagsonBlog() {
        Author user = new Author();
        user.setAuthorEmail("test@gmail.com");
        user.setAuthorPassword("1234");
        user.setAuthorName("Test Author");
        
        Author savedUser = repoAuth.save(user);
        
        Blog blog = new Blog();
        blog.setBlogName("Test Blog Post");
        blog.setBlogContent("This is a test of the blog tests.");
        blog.setAuthor(user);

        Blog savedBlog = repo.save(blog);
        
        List<Blog> blogList = new ArrayList<>();
        blogList.add(blog);
        
        Tag hash = new Tag();
        hash.setTagName("test");
        hash.setBlog(blogList);
        Tag hash2 = new Tag();
        hash.setTagName("test2");
        hash.setBlog(blogList);
        
        Tag savedTag = repoTag.save(hash);
        
        List<Tag> tagList = new ArrayList<>();
        tagList.add(hash);
        tagList.add(hash2);
        
        assertEquals(2, tagList.size());
        assertTrue(tagList.contains(hash2));
    }
    
    @org.junit.jupiter.api.Test
    public void testUpdateBlogContent() {
        Author user = new Author();
        user.setAuthorEmail("test@gmail.com");
        user.setAuthorPassword("1234");
        user.setAuthorName("Test Author");
        
        Author savedUser = repoAuth.save(user);
        
        Blog blog = new Blog();
        blog.setBlogName("Test Blog Post");
        blog.setBlogContent("This is a test of the blog tests.");
        blog.setAuthor(user);

        Blog savedBlog = repo.save(blog);

        Blog existingBlog = entityManager.find(Blog.class, savedBlog.getBlogId());
        
        List<Blog> blogList = new ArrayList<>();
        blogList.add(blog);
        
        Tag hash = new Tag();
        hash.setTagName("test");
        hash.setBlog(blogList);
        Tag hash2 = new Tag();
        hash.setTagName("test2");
        hash.setBlog(blogList);
        
        Tag savedTag = repoTag.save(hash);
        
        List<Tag> tagList = new ArrayList<>();
        tagList.add(hash);
        tagList.add(hash2);
        
        assertTrue(blog.getBlogContent().contentEquals(existingBlog.getBlogContent()));
        assertTrue(savedBlog.getBlogName().contentEquals(existingBlog.getBlogName()));
        assertTrue(existingBlog.getBlogName().equals("Test Blog Post"));
        String blogUpdate = blog.getBlogContent() + " Updated content.";
        blog.setBlogContent(blogUpdate);
        
        savedBlog = repo.save(blog);
        
        assertTrue(savedBlog.getBlogContent().contentEquals("This is a test of the blog tests. Updated content."));
        
    }
    
    public void testFindByApproval() {
        Author user = new Author();
        user.setAuthorEmail("test@gmail.com");
        user.setAuthorPassword("1234");
        user.setAuthorName("Test Author");
        
        Author savedUser = repoAuth.save(user);
        
        Blog blog = new Blog();
        blog.setBlogName("Test Blog Post");
        blog.setBlogContent("This is a test of the blog tests.");
        blog.setAuthor(user);
        blog.setBlogApproved(true);
        
        Blog blog2 = new Blog();
        blog2.setBlogName("Test #2 Blog Post");
        blog2.setBlogContent("This is another test of the blog tests.");
        blog2.setAuthor(user);
        blog2.setBlogApproved(false);

        Blog savedBlog = repo.save(blog);
        Blog savedBlog2 = repo.save(blog2);

        Blog existingBlog = entityManager.find(Blog.class, savedBlog.getBlogId());
        
        List<Blog> blogList = new ArrayList<>();
        blogList.add(blog);
        
        assertTrue(savedBlog.isBlogApproved());
        assertFalse(savedBlog.isBlogApproved());
        
        List<Blog> findBlog1 = repo.findByApproved();
                
        assertTrue(findBlog1.contains(blog));
    }
    
}
