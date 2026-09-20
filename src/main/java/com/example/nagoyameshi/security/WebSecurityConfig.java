package com.example.nagoyameshi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    // ① 管理者用チェーン：/admin/** のリクエストだけを処理する
    //    @Order(1) なので、まずこちらが「自分が担当するURLかどうか」をチェックする
    @Bean
    @Order(1)
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http,
            AdminDetailsServiceImpl adminDetailsService, PasswordEncoder passwordEncoder) throws Exception {

        // adminsテーブルを見て認証するプロバイダを作る
    	DaoAuthenticationProvider adminAuthProvider = new DaoAuthenticationProvider(adminDetailsService);
    	adminAuthProvider.setPasswordEncoder(passwordEncoder);


        http
            .securityMatcher("/admin/**")              // ← このチェーンが担当するURL範囲を限定
            .authenticationProvider(adminAuthProvider)  // ← このチェーンではAdminDetailsServiceImplを使う
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/admin/login").permitAll()  // ログイン画面自体は誰でもアクセス可
                .anyRequest().hasRole("ADMIN")                // それ以外の/admin/**はADMIN権限が必要
            )
            .formLogin((form) -> form
                .loginPage("/admin/login")
                .loginProcessingUrl("/admin/login")
                .defaultSuccessUrl("/admin/shops?loggedIn")
                .failureUrl("/admin/login?error")
                .permitAll()
            )
            .logout((logout) -> logout
                .logoutUrl("/admin/logout")
                .logoutSuccessUrl("/admin/login?loggedOut")
                .permitAll()
            );

        return http.build();
    }

    // ② 一般会員用チェーン：①でマッチしなかった残り全部を処理する
    @Bean
    @Order(2)
    public SecurityFilterChain userSecurityFilterChain(HttpSecurity http,
            UserDetailsServiceImpl userDetailsService, PasswordEncoder passwordEncoder) throws Exception {

        // usersテーブルを見て認証するプロバイダを作る
    	DaoAuthenticationProvider userAuthProvider = new DaoAuthenticationProvider(userDetailsService);
    	userAuthProvider.setPasswordEncoder(passwordEncoder);


        http
            .authenticationProvider(userAuthProvider)   // ← このチェーンではUserDetailsServiceImplを使う
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/css/**", "/images/**", "/js/**", "/storage/**", "/", "/signup/**", "/shops", "shops/{id}").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/?loggedIn")
                .failureUrl("/login?error")
                .permitAll()
            )
            .logout((logout) -> logout
                .logoutSuccessUrl("/?loggedOut")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
