package hospital.web.domain.dto.review;

import hospital.web.domain.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewInfoResponseFromUser {
    private String hospitalName;
    private String address;
    private String title;
    private String content;



    public ReviewInfoResponseFromUser(Review review) {
        this.hospitalName = review.getHospital().getHospitalName();
        this.address = review.getHospital().getRoadNameAddress();
        this.title = review.getTitle();
        this.content = review.getContent();
    }

}
