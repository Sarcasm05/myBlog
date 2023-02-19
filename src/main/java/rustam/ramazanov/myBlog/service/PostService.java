package rustam.ramazanov.myBlog.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import rustam.ramazanov.myBlog.dto.PostDto;
import rustam.ramazanov.myBlog.model.Post;
import rustam.ramazanov.myBlog.repository.PostRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public List<PostDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
    }

    public PostDto getPost(Long id) throws PostNotFoundException {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            return modelMapper.map(post.get(), PostDto.class);
        } else {
            throw new PostNotFoundException("Post not found with id " + id);
        }
    }

    public PostDto createPost(PostDto postDto) {
        Post post = modelMapper.map(postDto, Post.class);
        post = postRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }

    public PostDto updatePost(Long id, PostDto postDto) throws PostNotFoundException {
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            post.setTitle(postDto.getTitle());
            post.setContent(postDto.getContent());
            post = postRepository.save(post);
            return modelMapper.map(post, PostDto.class);
        } else {
            throw new PostNotFoundException("Post not found with id " + id);
        }
    }

    public void deletePost(Long id) throws PostNotFoundException {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            postRepository.delete(post.get());
        } else {
            throw new PostNotFoundException("Post not found with id " + id);
        }
    }
}

