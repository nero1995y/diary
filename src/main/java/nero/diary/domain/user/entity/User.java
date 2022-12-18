package nero.diary.domain.user.entity;

import lombok.AccessLevel;
import lombok.Builder;
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

    @Column(name = "user_email")
    private String email;

    @Column(name = "user_phone")
    private String phone;

    @Column(name = "user_password")
    private String password;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @Builder
    public User(String username, String email, String phone, String password, Diary diary) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        
        if(diary != null) {
            changeDiary(diary);
        }
    }

    public void changeDiary(Diary diary) {
        this.diary = diary;
        diary.getUsers().add(this);
    }


    public void update(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.phone = user.getPhone();
    }

}
