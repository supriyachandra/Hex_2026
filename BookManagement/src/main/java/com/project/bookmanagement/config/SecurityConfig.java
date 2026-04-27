package com.project.bookmanagement.config;


import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain bankingSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.OPTIONS,"/**")
                        .permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/book/add")
                        .authenticated()

                        .requestMatchers(HttpMethod.GET, "/api/book/get-all")
                        .hasAuthority("STUDENT")

                        .requestMatchers(HttpMethod.GET, "/api/book/get-by/{isbn}")
                        .hasAuthority("STUDENT")

                        .requestMatchers(HttpMethod.GET, "/api/book/update/{id}")
                        .hasAuthority("AUTHOR")

                        .requestMatchers(HttpMethod.DELETE, "/api/book/delete")
                        .hasAuthority("AUTHOR")

                        .requestMatchers(HttpMethod.POST, "/api/user/sign-up")
                        .authenticated()

                        .requestMatchers(HttpMethod.GET, "/api/auth/login")
                        .permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/book/get-all/author")
                        .hasAuthority("AUTHOR")

                        .requestMatchers(HttpMethod.GET, "/api/employee/get-all")
                        .permitAll()

                        .anyRequest().permitAll()
                );
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // jwt tokenization
        http.httpBasic(Customizer.withDefaults());  //--- using basic auth
        return http.build();
    }

}