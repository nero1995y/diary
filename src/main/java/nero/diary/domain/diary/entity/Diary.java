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

    @OneToMany(mappedBy = "diary")
    private List<User> users = new ArrayList<>();

    public Diary(String name, List<User> users) {
        this.name = name;
        this.users = users;
    }

    public Diary(String name) {
        this.name = name;
    }
}