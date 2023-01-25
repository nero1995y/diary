package nero.diary.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import nero.diary.domain.diary.entity.Diary;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nero.diary.domain.user.entity.auth.Session;

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

    @Column(name = "user_phone")
    private String phone;

    @Column(name = "user_password")
    private String password;

    @Column(name = "user_picture")
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Diary> diaryList = new ArrayList<>();


    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Session> sessions = new ArrayList<>();


    @Builder
    public User(String username, String email, String phone, String password, String picture, Role role, List<Diary> diaryList) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.picture = picture;
        this.role = role;
        
        if(diaryList != null) {
            changeDiary(diaryList);
        }
    }

    public void changeDiary(List<Diary> diaryList) {
        this.diaryList = diaryList;
    }

    public void update(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.phone = user.getPhone();
    }

    public User update(String name, String picture) {
        this.username = name;
        this.picture = picture;

        return this;
    }



    public Session addSession() {
        Session session = Session.builder()
                .user(this)
                .build();

        sessions.add(session);

        return session;

    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
