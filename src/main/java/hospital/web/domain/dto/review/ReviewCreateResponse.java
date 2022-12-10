package hospital.web.domain.dto.review;

import hospital.web.domain.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewCreateResponse {

    private String title;
    private String content;
    private String userAccount;
    private String message;

    public ReviewCreateResponse(Review review, String message) {
        this.title = review.getTitle();
        this.content = review.getContent();
        this.userAccount = review.getUser().getUserAccount();
        this.message = message;
    }
}
