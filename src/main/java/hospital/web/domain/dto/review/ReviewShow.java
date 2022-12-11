package hospital.web.domain.dto.review;

import hospital.web.domain.entity.Post;
import hospital.web.domain.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
public class ReviewShow {
    private String title;
    private String content;
    private String userAccount;
    private String createdDate;

    public ReviewShow(Review review, String userAccount) {

        this.title = review.getTitle();
        this.content = review.getContent();
        this.userAccount = userAccount;
        LocalDateTime created = review.getCreatedDate();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분");
        String format = created.format(dtf);
        this.createdDate = format;


    }
}
