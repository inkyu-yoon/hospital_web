package hospital.web.domain.entity;

import hospital.web.domain.dto.post.PostCreateRequest;
import hospital.web.domain.dto.post.PostUpdateRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Post extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private String title;
    private String content;
    private String isUpdated;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;


    public Post(PostCreateRequest postCreateRequest,User user) {
        this.title = postCreateRequest.getTitle();
        this.content = postCreateRequest.getContent();
        this.user = user;
    }

    public Post(PostUpdateRequest postUpdateRequest,User user) {
        this.id = postUpdateRequest.getId();
        this.title = postUpdateRequest.getTitle();
        this.content = postUpdateRequest.getContent();
        this.user = user;
        this.isUpdated = "(수정됨)";
    }
}
