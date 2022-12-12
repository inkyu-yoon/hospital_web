package hospital.web.controller;

import hospital.web.domain.dto.comment.CommentShowByFrom;
import hospital.web.domain.dto.post.PostCreateRequest;
import hospital.web.domain.dto.post.PostShow;
import hospital.web.domain.dto.post.PostShowList;
import hospital.web.domain.dto.post.PostUpdateRequest;
import hospital.web.domain.entity.Post;
import hospital.web.domain.entity.User;
import hospital.web.service.CommentService;
import hospital.web.service.PostService;
import hospital.web.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final CommentService commentService;
    private final BCryptPasswordEncoder encoder;

    @GetMapping("/new")
    public String createPage() {
        return "posts/new";
    }

    @GetMapping("/list")
    public String showList(Model model) {
        List<PostShowList> posts = postService.getAll().stream().map(post -> new PostShowList(post,post.getUser().getUserAccount())).collect(Collectors.toList());
        model.addAttribute("posts", posts);
        return "posts/list";
    }

    @GetMapping("")
    public String show() {
        return "redirect:/posts/list";
    }

    @GetMapping("/{id}")
    public String showOne(@PathVariable(name = "id") Long id, Model model) {
        Optional<Post> optPost = postService.getOne(id);

        if (!optPost.isEmpty()) {
            PostShow postShow = new PostShow(optPost.get(),optPost.get().getUser().getUserAccount(),
                    commentService.findAllCommentByPostId(optPost.get().getId()).stream().map(comment -> new CommentShowByFrom(comment)).collect(Collectors.toList()));
            log.info("{}",optPost.get().getUser().getUserAccount());
            model.addAttribute("post", postShow);
            return "posts/show";
        } else {
            model.addAttribute("message", String.format("%d가 없습니다.", id));
            return "posts/error";
        }
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable(name = "id") Long id, Model model) {
        Optional<Post> optPost = postService.getOne(id);
        if (!optPost.isEmpty()) {
            model.addAttribute("post", optPost.get());
            return "posts/edit";
        } else {
            model.addAttribute("message", String.format("%d가 없습니다.", id));
            return "posts/error";
        }
    }

    @PostMapping("/{id}/update")
    public String update(PostUpdateRequest postUpdateRequest, Model model) {
        User user = userService.getUserByUserAccount(postUpdateRequest.getUserAccount());
        if (encoder.matches(postUpdateRequest.getPassword(), user.getPassword())) {
            Post updatedPost = new Post(postUpdateRequest, user);
            postService.createPost(updatedPost);
            return "redirect:/posts/" + updatedPost.getId();
        }
        model.addAttribute("message", "비밀번호가 일치하지 않습니다.");
        return "posts/error";

    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable(name = "id") Long id) {
        postService.deleteOne(id);
        return "redirect:/posts";
    }

    @PostMapping("")
    public String add(PostCreateRequest postCreateRequest,Model model) {
        User user = userService.getUserByUserAccount(postCreateRequest.getUserAccount());
        if (encoder.matches(postCreateRequest.getPassword(), user.getPassword())) {
            Post savedPost = postService.createPost(new Post(postCreateRequest, user));
            return "redirect:/posts/" + savedPost.getId();
        }
        model.addAttribute("message", "비밀번호가 일치하지 않습니다.");
        return "posts/error";
    }
}
