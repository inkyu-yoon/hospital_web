package hospital.web.controller;

import hospital.web.domain.dto.comment.CommentCreateByFrom;
import hospital.web.domain.entity.Comment;
import hospital.web.domain.entity.User;
import hospital.web.service.CommentService;
import hospital.web.service.PostService;
import hospital.web.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;
    private final BCryptPasswordEncoder encoder;


    @PostMapping("")
    public String createComment(CommentCreateByFrom commentCreateByFrom) {
        String userAccount = commentCreateByFrom.getUserAccount();

        if (encoder.matches(commentCreateByFrom.getPassword(), userService.getUserByUserAccount(userAccount).getPassword())) {
        Long postId = commentCreateByFrom.getPostId();
        Comment comment = new Comment(commentCreateByFrom, postService.getOne(postId).get(), userService.getUserByUserAccount(userAccount));
        commentService.createComment(comment);
        return "redirect:/posts/"+postId;
        }
        return "posts/error";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable(name = "id") Long id, String userAccount, String password) {
        User user = userService.getUserByUserAccount(userAccount);

        if (!encoder.matches(password, user.getPassword())) {
            return "posts/error";
        }
        Comment comment = commentService.getById(id);
        Long postId = comment.getPost().getId();
        commentService.deleteOne(id);
        return "redirect:/posts/" + postId;
    }

}
