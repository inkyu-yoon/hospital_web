package hospital.web.domain.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentCreateByFrom {
    private Long postId;
    private String userAccount;
    private String password;
    private String content;

}
