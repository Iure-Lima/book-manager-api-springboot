package org.book.bookmanager.configs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers(HttpMethod.POST, "/auth/**").permitAll();
                    authorize.requestMatchers(HttpMethod.POST, "/book").hasAnyRole("ADMIN", "LIBRARIAN");
                    authorize.requestMatchers(HttpMethod.DELETE, "/book").hasAnyRole("ADMIN", "LIBRARIAN");
                    authorize.requestMatchers(HttpMethod.GET, "/book/**").permitAll();
                    authorize.requestMatchers(HttpMethod.GET, "/book/books").permitAll();
                    authorize.requestMatchers(HttpMethod.POST, "/borrowed").hasRole("USER");
                    authorize.requestMatchers(HttpMethod.GET, "/borrowed/me").hasAnyRole("ADMIN", "USER");
                    authorize.requestMatchers(HttpMethod.GET, "/borrowed/email/{email}").hasAnyRole("ADMIN", "LIBRARIAN");
                    authorize.requestMatchers(HttpMethod.GET, "/borrowed/book/{id}").hasAnyRole("ADMIN", "LIBRARIAN");
                    authorize.requestMatchers(HttpMethod.GET, "/borrowed/all").hasAnyRole("ADMIN", "LIBRARIAN");
                    authorize.requestMatchers(HttpMethod.GET, "/borrowed/state/{state}").hasAnyRole("ADMIN", "LIBRARIAN");
                    authorize.requestMatchers(HttpMethod.GET, "/borrowed/me/state/{state}").hasRole("USER");
                    authorize.requestMatchers(HttpMethod.GET, "/borrowed/due/{date}").hasAnyRole("ADMIN", "LIBRARIAN");
                    authorize.requestMatchers(HttpMethod.GET, "/borrowed/checkout/{date}").hasAnyRole("ADMIN", "LIBRARIAN");
                    authorize.requestMatchers(HttpMethod.PATCH, "/borrowed/{id}").hasAnyRole("ADMIN", "LIBRARIAN");
                    authorize.requestMatchers(HttpMethod.DELETE, "/borrowed/{id}").hasRole("ADMIN");
                    authorize.requestMatchers(HttpMethod.POST, "/wishlist").hasRole("USER");
                    authorize.requestMatchers(HttpMethod.DELETE, "/wishlist").hasRole("USER");
                    authorize.requestMatchers(HttpMethod.POST, "/wishlist/addBook/{id}").hasRole("USER");
                    authorize.requestMatchers(HttpMethod.DELETE, "/wishlist/removeBook/{id}").hasRole("USER");
                    authorize.requestMatchers(HttpMethod.GET, "/wishlist").hasRole("USER");
                    authorize.requestMatchers(HttpMethod.GET, "/wishlist/{id}").hasRole("USER");
                    authorize.anyRequest().authenticated();
                }).addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return  authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring().requestMatchers("/swagger-ui/**", "/v3/api-docs/**");
    }
}
