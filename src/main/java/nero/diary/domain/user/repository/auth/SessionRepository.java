package nero.diary.domain.user.repository.auth;

import nero.diary.domain.user.entity.auth.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {

    Optional<Session> findByAccessToken(String accessToken);
}
