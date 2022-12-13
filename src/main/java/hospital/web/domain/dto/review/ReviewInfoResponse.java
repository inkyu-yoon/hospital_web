package hospital.web.domain.dto.review;

import hospital.web.domain.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewInfoResponse {
    private String address;
    private String title;
    private String content;
    private String userAccount;


    public ReviewInfoResponse(Review review) {
        this.address = review.getHospital().getRoadNameAddress();
        this.title = review.getTitle();
        this.content = review.getContent();
        this.userAccount = review.getUser().getUserAccount();

    }

}
