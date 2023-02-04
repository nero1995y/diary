package nero.diary.domain.user.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nero.diary.domain.diary.entity.Diary;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name")
    private String username;

    @Column(name = "user_email")
    private String email;

    @Column(name = "user_picture")
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Diary> diaryList = new ArrayList<>();

    @Builder
    public User(String username, String email, String picture, Role role, List<Diary> diaryList) {
        this.username = username;
        this.email = email;
        this.picture = picture;
        this.role = role;

        if (diaryList != null) {
            changeDiary(diaryList);
        }
    }

    public void changeDiary(List<Diary> diaryList) {
        this.diaryList = diaryList;
    }

    public void update(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }

    public User update(String name, String picture) {
        this.username = name;
        this.picture = picture;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
