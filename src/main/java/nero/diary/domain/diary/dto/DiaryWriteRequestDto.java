package nero.diary.domain.diary.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nero.diary.domain.diary.entity.Diary;
import nero.diary.domain.user.entity.User;

@Getter
@NoArgsConstructor
public class DiaryWriteRequestDto {

    private String name;
    private String content;

    private String username;

    private User user;

    @Builder
    public DiaryWriteRequestDto(String name, String content, String username, User user) {
        this.name = name;
        this.content = content;
        this.username = username;
        this.user  = user;
    }

    public Diary toEntity() {

        return Diary.builder()
                .name(this.name)
                .content(this.content)
                .user(this.user)
                .build();
    }
}
