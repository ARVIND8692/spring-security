package com.springmvc.config;

import com.springmvc.entity.User;
import com.springmvc.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.intercept.RunAsImplAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;


@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    DataSource dataSource;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    UserDetailsService userDetailsService;
    
    @PostConstruct
    void postConstruct(){
        User user =new User();
        user.setUsername("user");
        user.setPassword(passwordEncoder().encode("pass"));
        userRepository.save(user);
        
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder managerBuilder) throws Exception {
        managerBuilder
                //.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
                .authenticationProvider(runAsAuthenticationProvider())
                .authenticationProvider(daoAuthenticationProvider());
    }
    
    public AuthenticationProvider runAsAuthenticationProvider(){
        RunAsImplAuthenticationProvider runAsImplAuthenticationProvider=new RunAsImplAuthenticationProvider();
        runAsImplAuthenticationProvider.setKey("abc");
        return runAsImplAuthenticationProvider;
    }
    
    public AuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
    

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and() 
                .formLogin().permitAll()
                .and()
                .logout()
                .permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/doLogout","GET"));


    }
    
    @Bean
    PasswordEncoder passwordEncoder(){
        //return new StandardPasswordEncoder();
        return new BCryptPasswordEncoder();//password will be saved having $2a$10$ (10 in the default times hashing will happen
    }
}
