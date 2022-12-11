package hospital.web.domain.dto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewCreateByForm {
    private String title;
    private String content;
    private String userAccount;
    private String password;
    private Long hospitalId;
}
