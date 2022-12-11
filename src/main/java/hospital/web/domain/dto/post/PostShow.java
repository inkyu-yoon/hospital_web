package hospital.web.domain.dto.post;

import hospital.web.domain.entity.Post;
import hospital.web.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public PostShow(Post post, String userAccount) {
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
        if (post.getCreatedDate() != post.getUpdatedDate()) {
            this.isUpdated = "(수정됨)";
        }
    }
}
