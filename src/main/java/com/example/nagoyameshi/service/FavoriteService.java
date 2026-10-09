package com.example.nagoyameshi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Favorite;
import com.example.nagoyameshi.entity.Shop;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.FavoriteRepository;

@Service
public class FavoriteService {
	private final FavoriteRepository favoriteRepository;
	
	public FavoriteService(FavoriteRepository favoriteRepository) {
		this.favoriteRepository = favoriteRepository;
	}
	
	// お気に入り登録
	@Transactional
	public void create(Shop shop, User user) {
		Favorite favorite = new Favorite();
		favorite.setShop(shop);
		favorite.setUser(user);
		favoriteRepository.save(favorite);
	}
	
	// お気に入り解除
    @Transactional
    public void delete(Favorite favorite) {
        favoriteRepository.delete(favorite);
    }

    // すでにお気に入り登録済みかどうかを判定
    public boolean isFavorite(Shop shop, User user) {
        return favoriteRepository.findByShopAndUser(shop, user) != null;
    }
}
