package nero.diary.domain.user.entity;

import nero.diary.domain.diary.entity.Diary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserTest {

    @Autowired
    EntityManager em;

    @Test
    public void testEntity() {
        Diary diary1 = new Diary("test Diary");
        Diary diary2 = new Diary("test Diary");

        em.persist(diary1);
        em.persist(diary2);

        User user1 = new User("nero1","email","010","password", diary1);
        User user2 = new User("nero1","email","010","password", diary1);
        User user3 = new User("nero1","email","010","password", diary2);
        User user4 = new User("nero1","email","010","password", diary2);

        em.persist(user1);
        em.persist(user2);
        em.persist(user3);
        em.persist(user4);

        em.flush();
        em.clear();

        em.createQuery("select u from User u", User.class).getResultList();

    }

}