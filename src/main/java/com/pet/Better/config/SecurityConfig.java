package com.pet.Better.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    // Конфігурація для аутентифікації
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
//                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("select username,password,active from accountant where username=?")
                .authoritiesByUsernameQuery("select a.username, ar.roles from accountant a inner join accountant_roles ar on a.username = ar.accountant_name where a.username=?");
    }

    // Конфігурація для авторизації та доступу
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .requestMatchers("/home/**")
                .authenticated()
                .requestMatchers("/**")
                    .permitAll()



                .and()
                .formLogin()
                .defaultSuccessUrl("/home/acc")
                .permitAll()

                .and()
                .logout()
                .logoutUrl("/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/home")
                .permitAll();

    }


    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
