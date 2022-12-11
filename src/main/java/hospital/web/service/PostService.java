package hospital.web.service;

import hospital.web.domain.entity.Post;
import hospital.web.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public List<Post> getAll() {
        return postRepository.findAll();
    }

    public Optional<Post> getOne(Long id) {
        return postRepository.findById(id);
    }

    public void deleteOne(Long id) {
        postRepository.deleteById(id);

    }
}
