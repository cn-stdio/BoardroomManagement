package com.ectocyst.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Seagull_gby
 * @date 2019/3/5 20:10
 * Description: 安全框架
 */

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests().antMatchers(
                "/**" ,
                "/login/**").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().permitAll();

    }

}