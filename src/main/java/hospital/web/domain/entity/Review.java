package hospital.web.domain.entity;

import hospital.web.domain.dto.ReviewCreateRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false, length = 500)
    private String content;
    @Column(name = "user_id", nullable = false)
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    public Review(ReviewCreateRequest reviewCreateRequest,Hospital hospital) {
        this.title = reviewCreateRequest.getTitle();
        this.content = reviewCreateRequest.getContent();
        this.userId = reviewCreateRequest.getUserId();
        this.hospital = hospital;
    }
}
