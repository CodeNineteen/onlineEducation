package com.df.config;

import com.df.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    LoginFailureHandler loginFailureHandler;
    @Autowired
    LoginSuccessHandler loginSuccessHandler;
    @Autowired
    CaptchaFilter captchaFilter;
    @Autowired
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    JwtAccessDeniedHandler jwtAccessDeniedHandler;
    @Autowired
    UserDetailServiceImpl userDetailService;
    @Autowired
    JwtLogoutSuccessHandler jwtLogoutSuccessHandler;
    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
      JwtAuthenticationFilter  jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager());
      return jwtAuthenticationFilter;

    }
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
//    访问拦截白名单
    private static  final String[] URL_WHITELIST = {
        "/login",
        "/logout",
        "/captcha",
        "/favicon.ico"
};

    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
//        表单提交
        .formLogin()
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
//      退出
        .and()
        .logout()
        .logoutSuccessHandler(jwtLogoutSuccessHandler)

//        禁用session
        .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        配置拦截器规则
        .and()

                .authorizeRequests()
//                URL_WHITELIST不要被认证
                .antMatchers(URL_WHITELIST).permitAll()
//                所有请求都必须被认证，必须登录之后才能被访问
                .anyRequest().authenticated()
//        异常处理器
        .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
//        配置自定义的过滤器
        .and()
        .addFilter(jwtAuthenticationFilter())
        .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class)
        ;


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService);
    }
}
