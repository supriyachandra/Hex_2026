package com.project.amazecare.cofig;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain bankingSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.OPTIONS,"/**")
                        .permitAll()

                        // not working
                        .requestMatchers(HttpMethod.POST, "/api/patient/sign-up")
                        .permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/patient/create")
                        .hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.GET,"/api/doctor/get-all")
                        .permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/doctor/get/{id}")
                        .authenticated()

                        .requestMatchers(HttpMethod.GET, "/api/doctor/get-by/{specialization_id}")
                        .permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/schedule/get-by/{doctor_id}")
                        .authenticated()

                        .requestMatchers(HttpMethod.GET, "/api/schedule/get-time-by/{doctor_id}/{date}")
                        .authenticated()

                        .requestMatchers(HttpMethod.GET,"/api/patient/get/{id}")
                        .hasAuthority("DOCTOR")

                        .requestMatchers(HttpMethod.GET, "/api/patient/get-all")
                        .hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/schedule/add")
                        .hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/doctor/add-doctor")
                        .hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/specialization/get-all")
                        .permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/appointment/book/{doctor_id}")
                        .hasAuthority("PATIENT")

                        .requestMatchers(HttpMethod.PUT, "/api/appointment/confirm/{appointment-id}")
                        .hasAuthority("DOCTOR")

                        .requestMatchers(HttpMethod.PUT, "/api/appointment/reject/{appointment_id}")
                        .hasAuthority("DOCTOR")

                        .requestMatchers(HttpMethod.POST, "/api/admin/add")
                        .hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/appointment/book/{doctor_id}/patient_id")
                        .hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.POST, "api/consultation/add")
                        .hasAuthority("DOCTOR")

                        .requestMatchers(HttpMethod.PUT, "/api/appointment/reschedule/{appointment_id}")
                        .hasAuthority("PATIENT")

                        .requestMatchers(HttpMethod.PUT, "/api/appointment/cancel/{appointment_id}")
                        .hasAuthority("PATIENT")

                        .requestMatchers(HttpMethod.GET, "/api/appointment/get-all/v1")
                        .hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/appointment/get-all/filter")
                        .hasAuthority("DOCTOR")

                        .requestMatchers(HttpMethod.POST, "/api/admission/admit")
                        .hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/api/admission/discharge/{admission_id}")
                        .hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/prescription/add")
                        .hasAuthority("DOCTOR")

                        .requestMatchers(HttpMethod.POST, "/api/tests/add")
                        .hasAuthority("DOCTOR")

                        .anyRequest().permitAll()
                );
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // jwt tokenization
        http.httpBasic(Customizer.withDefaults());  //--- using basic auth
        return http.build();
    }

//    @Bean
//    public AuthenticationManager authenticationManager(
//            UserDetailsService userDetailsService,
//            PasswordEncoder passwordEncoder
//    ){
//        DaoAuthenticationProvider daoAuthenticationProvider= new DaoAuthenticationProvider(userDetailsService);
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//        return new ProviderManager(daoAuthenticationProvider);
//    }
}
