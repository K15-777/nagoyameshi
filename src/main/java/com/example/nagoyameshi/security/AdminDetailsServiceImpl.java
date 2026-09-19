package com.example.nagoyameshi.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.nagoyameshi.entity.Admin;
import com.example.nagoyameshi.repository.AdminRepository;

/*
 * 管理者（Admin）用のログイン処理を行うクラス。
 * メールアドレスをキーにadminsテーブルを検索し、AdminDetailsImplに変換する。
 */
@Service
public class AdminDetailsServiceImpl implements UserDetailsService {
	private final AdminRepository adminRepository;

	public AdminDetailsServiceImpl(AdminRepository adminRepository) {
		this.adminRepository = adminRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Admin admin = adminRepository.findByEmail(email);

		if (admin == null) {
			throw new UsernameNotFoundException("管理者が見つかりませんでした。");
		}

		Collection<GrantedAuthority> authorities = new ArrayList<>();
		// 管理者には一律ROLE_ADMINを付与する
		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

		return new AdminDetailsImpl(admin, authorities);
	}
}
