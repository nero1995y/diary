package nero.diary;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
class DiaryApplicationTests {

	@Autowired
	EntityManager em;

	@Test
	void contextLoads() {

	}

}
