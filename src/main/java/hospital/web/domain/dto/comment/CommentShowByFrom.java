package hospital.web.domain.dto.comment;

import hospital.web.domain.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
public class CommentShowByFrom {

    private Long id;
    private String userAccount;
    private String content;
    private String createdDate;

    public CommentShowByFrom(Comment comment) {
        this.id = comment.getId();
        this.userAccount = comment.getUser().getUserAccount();
        this.content = comment.getContent();

        LocalDateTime created = comment.getCreatedDate();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분");
        String format = created.format(dtf);
        this.createdDate = format;
    }
}
