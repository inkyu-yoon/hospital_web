package hospital.web.domain.entity;

import hospital.web.domain.dto.review.ReviewCreateByForm;
import hospital.web.domain.dto.review.ReviewCreateRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false, length = 500)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    public Review(ReviewCreateRequest reviewCreateRequest,Hospital hospital,User user) {
        this.title = reviewCreateRequest.getTitle();
        this.content = reviewCreateRequest.getContent();
        this.user = user;
        this.hospital = hospital;
    }

    public Review(ReviewCreateByForm reviewCreateByForm,Hospital hospital,User user) {
        this.title = reviewCreateByForm.getTitle();
        this.content = reviewCreateByForm.getContent();
        this.user = user;
        this.hospital = hospital;
    }
}
