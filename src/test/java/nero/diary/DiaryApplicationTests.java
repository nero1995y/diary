package nero.diary;

import com.querydsl.jpa.impl.JPAQueryFactory;
import nero.diary.domain.user.entity.QUser;
import nero.diary.domain.user.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class DiaryApplicationTests {

	@Autowired
	EntityManager em;

	@Test
	void contextLoads() {
		User user = new User();
		em.persist(user);

		JPAQueryFactory query = new JPAQueryFactory(em);
		QUser qUser = QUser.user;

		User result = query.selectFrom(qUser).fetchOne();

		assertThat(result).isEqualTo(user);



	}

}
