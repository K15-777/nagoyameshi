package com.example.nagoyameshi.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.UserRepository;

/*
 * 会員（User）用のログイン処理を行うクラス。
 * メールアドレスをキーにusersテーブルを検索し、UserDetailsImplに変換する。
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private final UserRepository userRepository;

	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		
		System.out.println("loadUserByName called.");
		
		if (user == null) {
			throw new UsernameNotFoundException("会員が見つかりませんでした。");
		}

		Collection<GrantedAuthority> authorities = new ArrayList<>();
		// 会員には一律ROLE_GENERALを付与する（有料/無料の判定はsubscriberカラムで別途行う）
		authorities.add(new SimpleGrantedAuthority("ROLE_GENERAL"));

		return new UserDetailsImpl(user, authorities);
	}
}
