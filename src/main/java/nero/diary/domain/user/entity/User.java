package nero.diary.domain.user.entity;

import lombok.AccessLevel;
import nero.diary.domain.diary.entity.Diary;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name")
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    public User(String username, Diary diary) {
        this.username = username;
        
        if(diary != null) {
            changeDiary(diary);
        }
    }

    public void changeDiary(Diary diary) {
        this.diary = diary;
        diary.getUsers().add(this);
    }
}
