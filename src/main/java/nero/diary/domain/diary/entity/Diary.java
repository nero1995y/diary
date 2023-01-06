package nero.diary.domain.diary.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nero.diary.domain.user.entity.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "diary_name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Diary(String name, User user) {
        this.name = name;

        if (user != null) {
            changeUser(user);
        }
    }

    public Diary(String name) {
        this.name = name;
    }

    public void changeUser(User user) {
        this.user = user;
        user.getDiaryList().add(this);
    }

}