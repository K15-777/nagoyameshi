package com.example.nagoyameshi.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.Shop;

public interface ShopRepository extends JpaRepository<Shop, Integer> {
	// キーワード（店舗名）検索
	public Page<Shop> findByNameLikeOrderByCreatedAtDesc(String nameKeyword, Pageable pageable);

	// キーワード（店舗名・住所）検索
	public Page<Shop> findByNameLikeOrAddressLikeOrderByCreatedAtDesc(String nameKeyword, String addressKeyword, Pageable pageable);

	// カテゴリ検索
	public Page<Shop> findByCategoryOrderByCreatedAtDesc(Category category, Pageable pageable);

	// 価格帯検索（highest_priceが指定価格以下）
	public Page<Shop> findByHighestPriceLessThanEqualOrderByLowestPriceAsc(Integer price, Pageable pageable);

	// 全件取得（新着順）
	public Page<Shop> findAllByOrderByCreatedAtDesc(Pageable pageable);

	// トップページ用おすすめ店舗
	public List<Shop> findTop10ByOrderByCreatedAtDesc();
}
