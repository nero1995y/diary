package nero.diary.domain.diary.repository;

import nero.diary.domain.diary.entity.Diary;
import nero.diary.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

    Optional<List<Diary>> findByNameAndUser(String name, User user);

    Optional<List<Diary>> findByUser(User user);
}
