package nero.diary.domain.diary.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nero.diary.global.entity.BaseTimeEntity;
import nero.diary.global.exception.ExceedeNameLength;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseTimeEntity {

    public static final int MAX_LENGTH = 45;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(nullable = false, name = "category_name")
    private String name;

    //@Builder
    public Category(String name) {
        this(null, name);
    }

    @Builder
    public Category(Long id, String name) {
        this.id = id;
        validateMaxLength(name);
        this.name = name;
    }

    private void validateMaxLength(String name) {
        if(name.length() > MAX_LENGTH) {
            throw  new ExceedeNameLength();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void update(String name) {
        this.name = name;
    }
}
