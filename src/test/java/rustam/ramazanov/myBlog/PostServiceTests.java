package rustam.ramazanov.myBlog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import rustam.ramazanov.myBlog.dto.PostDto;
import rustam.ramazanov.myBlog.dto.UserDto;
import rustam.ramazanov.myBlog.model.Post;
import rustam.ramazanov.myBlog.model.User;
import rustam.ramazanov.myBlog.repository.PostRepository;
import rustam.ramazanov.myBlog.service.PostNotFoundException;
import rustam.ramazanov.myBlog.service.PostService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class PostServiceTests {

    @Autowired
    private PostService postService;

    @MockBean
    private PostRepository postRepository;

    @Test
    void testGetAllPosts() {
        Post post1 = new Post();
        User user1 = new User();
        user1.setName("John Doe");
        post1.setId(1L);
        post1.setTitle("Title 1");
        post1.setContent("Content");
        post1.setAuthor(user1);

        Post post2 = new Post();
        User user2 = new User();
        user2.setName("John Doe2");
        post2.setId(2L);
        post2.setTitle("Title 2");
        post2.setContent("Content 2");
        post2.setAuthor(user2);
        List<Post> posts = List.of(post1, post2);

        when(postRepository.findAll()).thenReturn(posts);

        List<PostDto> postDtos = postService.getAllPosts();

        assertEquals(2, postDtos.size());
        assertEquals("Title 1", postDtos.get(0).getTitle());
        assertEquals("Content 2", postDtos.get(1).getContent());
    }

    @Test
    void testGetPost() throws PostNotFoundException {
        Post post = new Post();
        post.setTitle("Title");
        post.setContent("Content");

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        PostDto postDto = postService.getPost(1L);

        assertEquals("Title", postDto.getTitle());
        assertEquals("Content", postDto.getContent());
    }

    @Test
    void testCreatePost() {
        PostDto postDto = new PostDto();
        UserDto userDto = new UserDto();
        userDto.setName("John Doe");
        postDto.setTitle("Title");
        postDto.setContent("Content");
        postDto.setAuthor(userDto);
        Post post = new Post();
        User user = new User();
        user.setName("John Doe");
        post.setId(1L);
        post.setTitle("Title");
        post.setContent("Content");
        post.setAuthor(user);

        when(postRepository.save(any(Post.class))).thenReturn(post);

        PostDto savedPostDto = postService.createPost(postDto);

        assertNotNull(savedPostDto.getId());
        assertEquals("Title", savedPostDto.getTitle());
        assertEquals("Content", savedPostDto.getContent());
        assertEquals("John Doe", savedPostDto.getAuthor().getName());
    }

    @Test
    void testUpdatePost() throws PostNotFoundException {
        PostDto postDto = new PostDto();
        UserDto userDto = new UserDto();
        userDto.setName("John Doe");
        postDto.setTitle("Updated Title");
        postDto.setContent("Updated Content");
        postDto.setAuthor(userDto);
        Post post = new Post();
        User user = new User();
        user.setName("John Doe");
        post.setId(1L);
        post.setTitle("Title");
        post.setContent("Content");
        post.setAuthor(user);

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class))).thenReturn(post);

        PostDto updatedPostDto = postService.updatePost(1L, postDto);

        assertEquals("Updated Title", updatedPostDto.getTitle());
        assertEquals("Updated Content", updatedPostDto.getContent());
    }

    @Test
    void testDeletePost() throws PostNotFoundException {
        Post post = new Post();

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        postService.deletePost(1L);

        verify(postRepository, times(1)).delete(post);
    }
}