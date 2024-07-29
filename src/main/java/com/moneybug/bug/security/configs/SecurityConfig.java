package com.moneybug.bug.security.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/images/**", "/js/**", "/favicon.*", "/*/icon-*").permitAll()
                        .requestMatchers("/", "/join", "/joinProc", "/login").permitAll()
                        .requestMatchers("/admin").hasAnyRole("ADMIN")
                        //.requestMatchers("/main/job").hasAnyRole("jobSeeker")
                        //.requestMatchers("/main/owner").hasAnyRole( "businessOwner")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/loginProc")
                        .failureHandler((request, response, exception) -> {
                            System.out.println("exception: " + exception.getMessage());
                            response.sendRedirect("/login");
                        })
                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
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

    /*@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }*/

    /*@Bean
    public UserDetailsService userDetailsService() {

        UserDetails user1 = User.builder()
                .username("user1")
                .password(passwordEncoder().encode("1234"))
                .roles("jobSeeker")
                .build();

        UserDetails user2 = User.builder()
                .username("user2")
                .password(passwordEncoder().encode("1234"))
                .roles("businessOwner")
                .build();

        UserDetails admin = User.builder()
                .username("ADMIN")
                .password("1234")
                .roles("ADMIN")
                .build();

        UserDetails user3 = User.builder()
                .username("user3")
                .password("{noop}1234")  // 인코딩 없이 저장
                .roles("businessOwner")
                .build();

        return new InMemoryUserDetailsManager(user1, user2, admin, user3);
    }*/

    //권한 계층
    @Bean
    public RoleHierarchy roleHierarchy() {

        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();

        hierarchy.setHierarchy("ROLE_ADMIN > ROLE_businessOwner\n" +
                "ROLE_ADMIN > ROLE_jobSeeker");

        return hierarchy;
    }
}
