package securityproject.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import securityproject.models.Student;
import securityproject.repositories.StudentRepo;

@Configuration
@EnableWebSecurity
public class WebAppSecurity {

    private final StudentRepo studentRepo;

    public WebAppSecurity(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    // authentication    // authorization
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(authorize -> authorize
                        .antMatchers("/welcome").permitAll()
                        .antMatchers("/api/students/save/page").permitAll()
                        .antMatchers(HttpMethod.POST,"/api/students/save").permitAll()
                        .anyRequest().authenticated()
                ).formLogin()
                .defaultSuccessUrl("/api/students", true)
                .permitAll();
        return http.build();
    }

    @Bean
    AuthenticationProvider authenticationManager() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(email -> {
            Student student = studentRepo.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException(
                            email + " not found!"
                    ));
            return new AuthUser(student);
        });
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
