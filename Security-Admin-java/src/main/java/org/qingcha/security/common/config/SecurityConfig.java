package org.qingcha.security.common.config;

import lombok.RequiredArgsConstructor;
import org.qingcha.security.common.security.JwtLogoutSuccessHandler;
import org.qingcha.security.common.security.LoginFailureHandler;
import org.qingcha.security.common.security.LoginSuccessHandler;
import org.qingcha.security.common.security.UserDetailsServiceImpl;
import org.qingcha.security.common.security.filter.JwtAuthenticationEntryPoint;
import org.qingcha.security.common.security.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Security configure
 * @author qingcha
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtLogoutSuccessHandler jwtLogoutSuccessHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final String[] WHITELIST = {
            "/login", "/logout", "/captcha", "/password", "/image/**"
    };

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 开启跨域 以及csrf攻击 关闭
        http
            .cors()
            .and()
                .csrf()
                .disable()
        // 登录登出配置
            .formLogin()
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
            .and()
                .logout()
                .logoutSuccessHandler(jwtLogoutSuccessHandler)
        // session禁用
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        // 拦截规则配置
            .and()
                .authorizeRequests()
                .antMatchers(WHITELIST).permitAll()  // 方向白名单
                .anyRequest().authenticated()
        // 异常配置
            .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)

        // 自定义过滤器配置
            .and()
                .addFilter(jwtAuthenticationFilter());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        return new JwtAuthenticationFilter(authenticationManager());
    }
}
