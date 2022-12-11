package hospital.web.controller;

import hospital.web.domain.dto.post.PostCreateRequest;
import hospital.web.domain.dto.post.PostUpdateRequest;
import hospital.web.domain.entity.Post;
import hospital.web.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/new")
    public String createPage(){
        return "posts/new";
    }

    @GetMapping("/list")
    public String showList(Model model) {
        List<Post> posts = postService.getAll();
        model.addAttribute("posts", posts);
        return "posts/list";
    }
    @GetMapping("")
    public String show(){
        return "redirect:/posts/list";
    }

    @GetMapping("/{id}")
    public String showOne(@PathVariable(name = "id") Long id, Model model) {
        Optional<Post> optPost = postService.getOne(id);
        if (!optPost.isEmpty()) {
            model.addAttribute("post", optPost.get());
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
    public String update(PostUpdateRequest postUpdateRequest) {
        Post updatedPost = new Post(postUpdateRequest);
        postService.createPost(updatedPost);
        return "redirect:/posts/" + updatedPost.getId();
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable(name = "id") Long id) {
        postService.deleteOne(id);
        return  "redirect:/posts";
    }

    @PostMapping("")
    public String add(PostCreateRequest postCreateRequest) {
        Post savedPost = postService.createPost(new Post(postCreateRequest));
        return "redirect:/posts/" + savedPost.getId();
    }
}
