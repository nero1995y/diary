package nero.diary.global.api;

import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

import static org.assertj.core.api.Assertions.*;

class ProfileControllerUnitTest {

    @Test
    void realProfileSearch() {
        // given
        String expectedProfile = "real";

        MockEnvironment env = new MockEnvironment();

        env.addActiveProfile(expectedProfile);
        env.addActiveProfile("oauth");
        env.addActiveProfile("real-db");

        ProfileController controller = new ProfileController(env);


        // when
        String profile = controller.profile();

        //
        assertThat(profile).isEqualTo(expectedProfile);

    }

    @Test
    public void  realProfileNotFound() {
        // given
        String expectedProfile = "oauth";

        MockEnvironment env = new MockEnvironment();

        env.addActiveProfile(expectedProfile);
        env.addActiveProfile("real-db");

        ProfileController controller = new ProfileController(env);


        // when
        String profile = controller.profile();

        // then
        assertThat(profile).isEqualTo(expectedProfile);
    }

    @Test
    public void  realProfileNotFoundDefault() {
        // given
        String expectedProfile = "default";

        MockEnvironment env = new MockEnvironment();

        env.addActiveProfile(expectedProfile);
        env.addActiveProfile("real-db");

        ProfileController controller = new ProfileController(env);


        // when
        String profile = controller.profile();

        // then
        assertThat(profile).isEqualTo(expectedProfile);
    }
}