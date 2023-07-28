package com.example.quanlyphattudemo.config;


import com.example.quanlyphattudemo.Security.jwt.JwtEntryPoin;
import com.example.quanlyphattudemo.Security.jwt.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final JwtEntryPoin jwtEntryPoin;

    private final LogoutHandler logoutHandler;
    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        return new JwtTokenFilter();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // cấu hình security
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    // khoi taobean dung cho login khoi tao authen hethong
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    // cấu hình security
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()

                .and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v6/auth/**").permitAll() // đầu ra api được phép truy cập hết
                //     .antMatchers("/api/v1/test/**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/test/admin").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.POST,"/api/version1.0/daoTrangs/them").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.POST,"/api/version1.0/chuas/them").hasAnyAuthority("ADMIN")
                .anyRequest().authenticated() //  chặn truy cập các ánh xạ khác
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtEntryPoin) // hứng lỗi khi authen sai
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .logout()
                .logoutUrl("/api/v6/auth/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication)
                        -> SecurityContextHolder.clearContext());
        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}
