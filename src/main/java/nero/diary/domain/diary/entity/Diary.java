package nero.diary.domain.diary.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nero.diary.domain.user.entity.User;
import nero.diary.global.entity.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "diary_name")
    private String name;


    @Column(name = "diary_content")
    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;


    @Builder
    public Diary(String name, String content, User user, Category category) {
        this.name = name;
        this.content = content;
        this.category = category;

        if (user != null) {
            changeUser(user);
        }
    }

    public Diary(String name) {
        this.name = name;
    }

    public void changeUser(User user) {
        this.user = user;
    }


    public void update(String name, String content, User user, Category category) {
        this.name = name;
        this.content = content;
        this.user = user;
        this.category = category;
    }

    public void updateCategory(Category category) {
        this.category = category;
    }
}