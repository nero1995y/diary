package nero.diary.global.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.security.config.BeanIds;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.DelegatingFilterProxy;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProfileController.class)
public class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("profile 인증없이 호출이 된다")
    @Test
    void profile() throws Exception {
        // given
        String expected = "default";


        DelegatingFilterProxy delegatingFilterProxy = new DelegatingFilterProxy();

        delegatingFilterProxy.init(new MockFilterConfig());


        mockMvc.perform(get("/profile")
                        .with(SecurityMockMvcRequestPostProcessors.user("test")))
                .andExpect(status().isOk())
                .andDo(print());


    }
}
