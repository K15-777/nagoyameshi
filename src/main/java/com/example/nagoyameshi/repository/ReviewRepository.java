package com.example.nagoyameshi.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.entity.Shop;
import com.example.nagoyameshi.entity.User;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
	// 店舗詳細ページで直近6件表示する用
	public List<Review> findTop6ByShopOrderByCreatedAtDesc(Shop shop);

	// レビュー一覧ページ（ページネーション付き）
	public Page<Review> findByShopOrderByCreatedAtDesc(Shop shop, Pageable pageable);

	// 特定ユーザーがその店舗にすでにレビューしているか確認する用
	public Review findFirstByShopAndUser(Shop shop, User user);
}
