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

	}

}
