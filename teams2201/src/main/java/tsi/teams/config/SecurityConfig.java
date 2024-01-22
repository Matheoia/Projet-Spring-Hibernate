package tsi.teams.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
//        UserDetails user = User.withUsername("user")
//                .password(passwordEncoder.encode("password"))
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.withUsername("admin")
//                .password(passwordEncoder.encode("admin"))
//                .roles("USER", "ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http.authorizeHttpRequests((requests) -> requests
//                    .requestMatchers("/home/**").permitAll()
//                    .requestMatchers("/h2/**").permitAll()
//                    .requestMatchers("/css/**").permitAll()
//                    .anyRequest().permitAll()
//                    //.anyRequest().authenticated()
//        );
//        return http.build();
//    }

}

//                .formLogin(formLogin ->
//                formLogin
//                        .loginPage("/login")
//                        .permitAll())
//                .logout((logout) -> logout.logoutUrl("/logout"));
//                .logout(logout ->
//                logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/home")
//                        .invalidateHttpSession(true)
//                        .deleteCookies("JSESSIONID")