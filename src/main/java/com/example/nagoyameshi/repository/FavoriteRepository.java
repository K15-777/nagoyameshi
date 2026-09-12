package com.example.nagoyameshi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nagoyameshi.entity.Favorite;
import com.example.nagoyameshi.entity.Shop;
import com.example.nagoyameshi.entity.User;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
	// ユーザーと店舗を指定してお気に入り登録状況を確認（詳細画面用）
	public Favorite findByShopAndUser(Shop shop, User user);

	// ログイン中のユーザーのお気に入り一覧を取得（ページネーション付き）
	public Page<Favorite> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
}
