package com.techmahindra.customers.configuration.security;


import com.techmahindra.customers.configuration.security.jwt.JWTAuthenticationFilter;
import com.techmahindra.customers.configuration.security.jwt.JWTLoginFilter;
import com.techmahindra.customers.configuration.security.jwt.TokenAuthenticationHelper;
import com.techmahindra.customers.configuration.security.userdetails.ApplicationUserDetailsManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JWTAuthenticationFilter jwtAuthenticationFilter;

    private final TokenAuthenticationHelper tokenAuthenticationHelper;

    private final ApplicationUserDetailsManager userDetailsManager;

    private final EntryPointUnauthorizedHandler unauthorizedHandler;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsManager).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class)
                .csrf().disable()
                .exceptionHandling()
                    .authenticationEntryPoint(this.unauthorizedHandler)
                .and()
                .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers(HttpMethod.POST, "/users").permitAll()
                    .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JWTLoginFilter("/login", authenticationManager(), tokenAuthenticationHelper), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}