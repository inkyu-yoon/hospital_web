package hospital.web.domain.dto.post;

import hospital.web.domain.dto.comment.CommentShowByFrom;
import hospital.web.domain.entity.Post;
import hospital.web.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class PostShow {
    private Long id;
    private String title;
    private String content;
    private String userAccount;
    private String createdDate;
    private String updatedDate;
    private String isUpdated;
    private List<CommentShowByFrom> comments = new ArrayList<>();

    public PostShow(Post post, String userAccount,List<CommentShowByFrom> comments) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.userAccount = userAccount;
        LocalDateTime created = post.getCreatedDate();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분");
        String format = created.format(dtf);
        this.createdDate = format;
        LocalDateTime updated = post.getUpdatedDate();
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분");
        String format2 = updated.format(dtf2);
        this.updatedDate = format2;
        if (!created.equals(updated)) {
            this.isUpdated = "(수정됨)";
        }
    }
}
