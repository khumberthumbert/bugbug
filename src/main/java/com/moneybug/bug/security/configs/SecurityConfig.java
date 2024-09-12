package com.moneybug.bug.security.configs;

import com.moneybug.bug.users.dto.CustomUserDetails;
import com.moneybug.bug.users.service.CustomOAuth2UserService;
import com.moneybug.bug.users.service.CustomUserDetailsService;
//import com.moneybug.bug.users.service.KakaoOAuth2UserService;
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
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
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
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                /*.csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .ignoringRequestMatchers("/mail", "/login/oauth2/**") // CSRF 보호에서 제외할 URL
                )*/
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/images/**", "/js/**", "/favicon.*", "/*/icon-*", "/mail").permitAll()
                        .requestMatchers( "/join", "/joinProc", "/login","/oauth2/**", "/login/**", "/login/oauth2/**").permitAll()
                        .requestMatchers("/admin").hasAnyRole("ADMIN")
                        .requestMatchers("/main/user").hasAnyRole("USER")
                        .requestMatchers("/main/manager").hasAnyRole("MANAGER")
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
                .oauth2Login((oauth2) -> oauth2
                        .loginPage("/login")
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(customOAuth2UserService)))
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(new Http403ForbiddenEntryPoint())
                )
                .sessionManagement((auth) -> auth
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true))
                .sessionManagement((session) -> session
                        .sessionFixation().changeSessionId())
                .exceptionHandling(exception ->
                        exception.accessDeniedHandler(new AccessDeniedHandlerImpl())
                )

        ;
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    //권한 계층
    @Bean
    public RoleHierarchy roleHierarchy() {

        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();

        hierarchy.setHierarchy("ROLE_ADMIN > ROLE_MANAGER\n" +
                "ROLE_MANAGER > ROLE_USER");
        return hierarchy;
    }
}
