package hospital.web.domain.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class PostCreateRequest {
    private String title;
    private String content;
    private String userAccount;
    private String password;

}
