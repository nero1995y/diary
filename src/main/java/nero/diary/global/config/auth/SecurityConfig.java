package nero.diary.global.config.auth;

import lombok.RequiredArgsConstructor;
import nero.diary.domain.user.entity.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService oAuth2UserService;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().frameOptions().disable()
            .and()
                .authorizeRequests()
                    .antMatchers("/","/css/","/images/**",
                        "/js/**", "/h2-console/**", "/profile","/api/v2/**").permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()
            .and()
                .logout()
                    .logoutSuccessUrl("/")
            .and()
                .oauth2Login()
                    .userInfoEndpoint()
                        .userService(oAuth2UserService);

        return http.build();

    }
}
