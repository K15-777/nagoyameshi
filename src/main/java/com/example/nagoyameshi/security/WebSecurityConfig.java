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

/*
 * nagoyameshiでは「会員（User）」と「管理者（Admin）」を別テーブルで管理しているため、
 * Spring Securityの認証も2系統用意する。
 *
 * ポイント：
 * 1. SecurityFilterChainを2つ定義し、securityMatcher()で担当するURLパスを分離する。
 *    - adminSecurityFilterChain  → "/admin/**" を担当（管理者ログイン）
 *    - userSecurityFilterChain   → それ以外すべてを担当（会員ログイン）
 * 2. どちらのチェーンが先に評価されるかを@Orderで明示する（数字が小さいほど優先）。
 *    "/admin/**" のように限定的なパターンを持つチェーンを先に評価しないと、
 *    後述の「それ以外すべて」にマッチするチェーンに横取りされてしまう。
 * 3. それぞれ専用のDaoAuthenticationProviderを用意し、
 *    UserDetailsServiceImpl（会員用） / AdminUserDetailsServiceImpl（管理者用）を紐付ける。
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

	// ==================== 管理者用 ====================
	@Bean
	@Order(1)
	public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http, AdminUserDetailsServiceImpl adminUserDetailsService) throws Exception {
		http
			.securityMatcher("/admin/**") // このチェーンが担当するURLパスを限定する
			.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/admin/login").permitAll()          // 管理者ログインページは誰でもアクセス可
				.anyRequest().hasRole("ADMIN")                        // それ以外の管理者ページはROLE_ADMINが必要
			)
			.formLogin((form) -> form
				.loginPage("/admin/login")
				.loginProcessingUrl("/admin/login")
				.defaultSuccessUrl("/admin", true)
				.failureUrl("/admin/login?error")
				.permitAll()
			)
			.logout((logout) -> logout
				.logoutUrl("/admin/logout")
				.logoutSuccessUrl("/admin/login?logout")
				.permitAll()
			)
			.authenticationProvider(adminAuthenticationProvider(adminUserDetailsService));

		return http.build();
	}

	@Bean
	public DaoAuthenticationProvider adminAuthenticationProvider(AdminUserDetailsServiceImpl adminUserDetailsService) {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(adminUserDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	// ==================== 会員用 ====================
	@Bean
	@Order(2)
	public SecurityFilterChain userSecurityFilterChain(HttpSecurity http, UserDetailsServiceImpl userDetailsService) throws Exception {
		http
			.authorizeHttpRequests((requests) -> requests
				.requestMatchers(
					"/css/**", "/images/**", "/js/**", "/storage/**",
					"/", "/signup/**", "/login",
					"/shops", "/shops/{id}"
				).permitAll() // 誰でもアクセスできるURL（TOPページ・会員登録・店舗一覧/詳細など）
				.anyRequest().hasRole("GENERAL") // それ以外は会員ログインが必要
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
			)
			.authenticationProvider(userAuthenticationProvider(userDetailsService));

		return http.build();
	}

	@Bean
	public DaoAuthenticationProvider userAuthenticationProvider(UserDetailsServiceImpl userDetailsService) {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
