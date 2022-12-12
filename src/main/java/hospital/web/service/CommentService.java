package hospital.web.service;

import hospital.web.domain.entity.Comment;
import hospital.web.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public void createComment(Comment comment) {
        commentRepository.save(comment);
    }

    public List<Comment> findAllCommentByPostId(Long postId) {
        return commentRepository.findByPost_Id(postId);
    }

}
