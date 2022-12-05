package hospital.web.domain.dto;

import hospital.web.domain.entity.Hospital;
import hospital.web.domain.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewCreateRequest {

    private String title;
    private String content;
    private String userId;
    private Long hospitalId;

}
