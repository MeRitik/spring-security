package com.ritik.security.config;

import com.ritik.security.service.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MyUserDetailsService myUserDetailsService;

    public SecurityConfig(MyUserDetailsService myUserDetailsService) {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2A);
        this.myUserDetailsService = myUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(o -> o.disable()); or
        http
                .csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(request -> request.anyRequest().authenticated())
                .formLogin(Customizer.withDefaults()) // For Browsers -- Disable for httpBasic like popup
                .httpBasic(Customizer.withDefaults()) // For Postman
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//                .userDetailsService(username -> username)
        ;
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
        authProvider.setUserDetailsService(myUserDetailsService);

        return authProvider;
    }

//    @Bean
//    UserDetailsService userDetailsService() {
//        UserDetails user1 = User.withDefaultPasswordEncoder().username("user1").password("password1").roles("USER").build();
//        UserDetails user2 = User.withDefaultPasswordEncoder().username("user2").password("password2").roles("TRAINER").build();
//        return new InMemoryUserDetailsManager(user1, user2); // ...varargs


//    }
}
