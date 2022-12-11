package hospital.web.domain.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostUpdateRequest {
    private Long id;
    private String title;
    private String content;
    private String userAccount;
    private String password;
}
