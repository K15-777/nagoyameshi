package com.example.nagoyameshi.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nagoyameshi.entity.Shop;

public interface ShopRepository extends JpaRepository<Shop, Integer> {
	public Page<Shop> findByNameLike(String keyword, Pageable pageable);
	
	public Page<Shop> findByNameLikeOrAddressLikeOrderByCreatedAtDesc(String nameKeyword, String addressKeyword, Pageable pageable);
	public Page<Shop> findByNameLikeOrAddressLikeOrderByLowestPriceAsc(String nameKeyword, String addressKeyword, Pageable pageable);
	public Page<Shop> findByLowestPriceGreaterThanEqualAndHighestPriceLessThanEqualOrderByCreatedAtDesc(Integer minPrice, Integer maxPrice, Pageable pageable);
	public Page<Shop> findByLowestPriceGreaterThanEqualAndHighestPriceLessThanEqualOrderByLowestPriceAsc(Integer minPrice, Integer maxPrice, Pageable pageable);
    public Page<Shop> findAllByOrderByCreatedAtDesc(Pageable pageable);
    public Page<Shop> findAllByOrderByLowestPriceAsc(Pageable pageable); 
	
	public List<Shop> findTop10ByOrderByCreatedAtDesc();
}
