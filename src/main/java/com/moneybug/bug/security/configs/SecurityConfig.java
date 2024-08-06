package com.moneybug.bug.security.configs;

import com.moneybug.bug.users.dto.CustomUserDetails;
import com.moneybug.bug.users.service.CustomUserDetailsService;
import com.moneybug.bug.users.service.KakaoOAuth2UserService;
import com.moneybug.bug.users.service.TokenRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@Log4j2
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final TokenRepositoryImpl tokenRepositoryImpl;
    private final KakaoOAuth2UserService kakaoOAuth2UserService;
    private final ClientRegistrationRepository clientRegistrationRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .ignoringRequestMatchers("/mail") // CSRF 보호에서 제외할 URL
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/images/**", "/js/**", "/favicon.*", "/*/icon-*", "/mail").permitAll()
                        .requestMatchers( "/join", "/joinProc", "/login").permitAll()
                        .requestMatchers("/admin").hasAnyRole("ADMIN")
                        .requestMatchers("/main/job").hasAnyRole("jobSeeker")
                        .requestMatchers("/main/owner").hasAnyRole("businessOwner")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/loginProc")
                        .failureHandler((request, response, exception) -> {
                            log.info("exception : {}", exception.getMessage());
                            response.sendRedirect("/login");
                        })
                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                .rememberMe(rememberMe -> rememberMe
                    .key("rememberMe")
                    .tokenValiditySeconds(86400)
                    .tokenRepository(tokenRepositoryImpl)
                    .userDetailsService(customUserDetailsService)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .clientRegistrationRepository(clientRegistrationRepository)
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(kakaoOAuth2UserService))
                        .successHandler((request, response, authentication) -> {
                            response.sendRedirect("/callback?code=" + request.getParameter("code"));
                        })
                )
                /*
                 * sessionManagement() 메소드를 통한 설정을 진행한다.
                 * maximumSession(정수) : 하나의 아이디에 대한 다중 로그인 허용 개수
                 * maxSessionPreventsLogin(boolean) : 다중 로그인 개수를 초과 하였을 경우 처리 방법
                      true : 초과시 새로운 로그인 차단
                      false : 초과시 기존 세션 하나 삭제
                 */
                .sessionManagement((auth) -> auth
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true))
                /*
                Session Fixation Attack 방지
                 */
                .sessionManagement((session) -> session
                        .sessionFixation().changeSessionId())
        ;
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    //권한 계층
    @Bean
    public RoleHierarchy roleHierarchy() {

        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();

        hierarchy.setHierarchy("ROLE_ADMIN > ROLE_businessOwner\n" +
                "ROLE_ADMIN > ROLE_jobSeeker");
        return hierarchy;
    }
}
