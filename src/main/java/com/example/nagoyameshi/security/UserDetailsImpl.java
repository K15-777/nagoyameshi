package com.example.nagoyameshi.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.nagoyameshi.entity.User;

/*
 * 会員（User）用のUserDetails実装クラス。
 * Spring Securityが認証・認可の判定に使うために、
 * usersテーブルのUserエンティティをラップしています。
 */
public class UserDetailsImpl implements UserDetails {
	private final User user;
	private final Collection<GrantedAuthority> authorities;

	public UserDetailsImpl(User user, Collection<GrantedAuthority> authorities) {
		this.user = user;
		this.authorities = authorities;
	}

	// ログイン中の会員情報をコントローラー側から取り出すためのメソッド
	public User getUser() {
		return user;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// ログインIDとしてメールアドレスを使用する
		return user.getEmail();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		// メール認証（本人確認）が完了しているかどうか
		return user.getEnabled();
	}
}
