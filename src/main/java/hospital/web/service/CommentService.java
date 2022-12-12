package hospital.web.service;

import hospital.web.domain.entity.Comment;
import hospital.web.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public void createComment(Comment comment) {
        commentRepository.save(comment);
    }

    public List<Comment> findAllCommentByPostId(Long postId) {
        return commentRepository.findByPost_Id(postId);
    }

    @Transactional
    public void deleteOne(Long id) {
        commentRepository.deleteById(id);
    }

    public Comment getById(Long id) {
        return commentRepository.findById(id).get();
    }
}
