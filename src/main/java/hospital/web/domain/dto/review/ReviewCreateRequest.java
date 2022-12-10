package hospital.web.domain.dto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewCreateRequest {

    private String title;
    private String content;
    private String userAccount;
    private Long hospitalId;

}
