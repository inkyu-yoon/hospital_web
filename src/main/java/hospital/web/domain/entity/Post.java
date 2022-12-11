package hospital.web.domain.entity;

import hospital.web.domain.dto.post.PostCreateRequest;
import hospital.web.domain.dto.post.PostUpdateRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private String title;
    private String content;


    public Post(PostCreateRequest postCreateRequest) {
        this.title = postCreateRequest.getTitle();
        this.content = postCreateRequest.getContent();
    }

    public Post(PostUpdateRequest postUpdateRequest) {
        this.id = postUpdateRequest.getId();
        this.title = postUpdateRequest.getTitle();
        this.content = postUpdateRequest.getContent();
    }
}
