//package com.mitrais.atm.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//import javax.sql.DataSource;
//
////@Configuration
////@EnableAutoConfiguration
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    static final String query = "SELECT * FROM ";
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Autowired
//    private void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication().dataSource(dataSource)
//                .usersByUsernameQuery("");
//    }
//}
