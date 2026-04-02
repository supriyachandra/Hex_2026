package com.springboot.myapp.config;

import com.springboot.myapp.service.UserService;
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
    //private final UserService userService;
    private final JwtFilter jwtFilter;

    /* This is for in memory auth
    @Bean
    public UserDetailsService users() {
        UserDetails customer1 = User.builder()
                .username("harry")
                .password("{noop}potter")
                .authorities("CUSTOMER")
                .build();
        UserDetails customer2 = User.builder()
                .username("ronald")
                .password("{noop}weasley")
                .authorities("CUSTOMER")
                .build();
        UserDetails executive1 = User.builder()
                .username("hermione")
                .password("{noop}granger")
                .authorities("EXECUTIVE")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}admin")
                .authorities("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(customer1,customer2,executive1, admin);
    }
*/
    @Bean
    public SecurityFilterChain bankingSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.OPTIONS,"/**")
                        .permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/admin/add")
                        .permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/customer/sign-up")
                        .permitAll()

                        .requestMatchers(HttpMethod.GET,"/api/auth/login")
                        .authenticated()

                        .requestMatchers(HttpMethod.GET,"/api/ticket/get-all")
                        .permitAll()

                        .requestMatchers(HttpMethod.GET,"/api/ticket/get/{id}")
                        .authenticated()

                        .requestMatchers(HttpMethod.GET,"/api/ticket/get-tickets")
                        .hasAuthority("CUSTOMER")

                        .requestMatchers(HttpMethod.POST, "/api/ticket/add")
                        .hasAnyAuthority("ADMIN","CUSTOMER")

                        .requestMatchers(HttpMethod.POST, "/api/customer/plan/assign-plan/{customerId}/{planId}")
                        .hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/customer/plan/buy-plan/{planId}")
                        .hasAuthority("CUSTOMER")

                        .requestMatchers(HttpMethod.PUT, "/api/ticket/add/{ticketId}/{executiveId}")
                        .hasAuthority("ADMIN")

                        .anyRequest().permitAll()
                );
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.httpBasic(Customizer.withDefaults());  //Spring understand that I am using this technique--- basic auth
        return http.build();
    }

//    /** This was my Phase-2 Auth Provider */
//    @Bean
//    public AuthenticationProvider authenticationManager(
//            UserDetailsService userDetailsService,
//            PasswordEncoder passwordEncoder) {
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
//        authenticationProvider.setPasswordEncoder(passwordEncoder());
//        return  authenticationProvider;
//    }
//    /** This is my Phase-3 JWT Manager */
//    @Bean
//    public  AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }
}
