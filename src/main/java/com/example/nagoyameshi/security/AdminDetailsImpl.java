package com.example.nagoyameshi.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.nagoyameshi.entity.Admin;

/*
 * 管理者（Admin）用のUserDetails実装クラス。
 * Spring Securityが認証・認可の判定に使うために、
 * adminsテーブルのAdminエンティティをラップしています。
 */
public class AdminDetailsImpl implements UserDetails {
	private final Admin admin;
	private final Collection<GrantedAuthority> authorities;

	public AdminDetailsImpl(Admin admin, Collection<GrantedAuthority> authorities) {
		this.admin = admin;
		this.authorities = authorities;
	}

	// ログイン中の管理者情報をコントローラー側から取り出すためのメソッド
	public Admin getAdmin() {
		return admin;
	}

	@Override
	public String getPassword() {
		return admin.getPassword();
	}

	@Override
	public String getUsername() {
		return admin.getEmail();
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
		return true; // adminsテーブルにenabledカラムがないため常にtrue
	}
}
