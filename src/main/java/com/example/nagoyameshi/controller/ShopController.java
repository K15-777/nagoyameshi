package com.example.nagoyameshi.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.nagoyameshi.entity.Favorite;
import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.entity.Shop;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReservationInputForm;
import com.example.nagoyameshi.repository.CategoryRepository;
import com.example.nagoyameshi.repository.FavoriteRepository;
import com.example.nagoyameshi.repository.ReviewRepository;
import com.example.nagoyameshi.repository.ShopRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;

@Controller
@RequestMapping("/shops")
public class ShopController {
	private final ShopRepository shopRepository;
	private final CategoryRepository categoryRepository;
	private final ReviewRepository reviewRepository;
	private final FavoriteRepository favoriteRepository;
	
	public ShopController(ShopRepository shopRepository,
						  CategoryRepository categoryRepository,
						  ReviewRepository reviewRepository,
						  FavoriteRepository favoriteRepository) 
	{
		this.shopRepository = shopRepository; 
		this.categoryRepository = categoryRepository;
		this.reviewRepository = reviewRepository;
		this.favoriteRepository = favoriteRepository;
	}
	
	@GetMapping
	public String index(@RequestParam(name = "keyword", required = false) String keyword,
						@RequestParam(name = "lowestPrice", required = false) Integer lowestPrice,
						@RequestParam(name = "highestPrice", required = false) Integer highestPrice,
						@RequestParam(name = "categoryId", required = false) Integer categoryId,
						@RequestParam(name = "order", required = false) String order,
						@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
						Model model)
	{
		Page<Shop> shopPage;
		
		if(categoryId != null) {
			//カテゴリ絞り込みの並び替え分岐
			if (order != null && order.equals("lowestPriceAsc")) {
				shopPage = shopRepository.findByCategoryIdOrderByLowestPriceAsc(categoryId, pageable);
			}else {
				shopPage = shopRepository.findByCategoryIdOrderByCreatedAtDesc(categoryId, pageable);
			}
		}else if(keyword != null && !keyword.isEmpty()) {
			if (order != null && order.equals("lowestPriceAsc")) {
				shopPage = shopRepository.findByNameLikeOrAddressLikeOrderByLowestPriceAsc("%" + keyword + "%", "%" + keyword + "%", pageable);
			}else {
				shopPage = shopRepository.findByNameLikeOrAddressLikeOrderByCreatedAtDesc("%" + keyword + "%", "%" + keyword + "%", pageable);
			}
		}else if(lowestPrice != null || highestPrice != null) {
			int min = (lowestPrice != null) ? lowestPrice : 0;
			int max = (highestPrice != null) ? highestPrice : Integer.MAX_VALUE;
			
			//価格検索の並び替え分岐
			if (order != null && order.equals("lowestPriceAsc")) {
				shopPage = shopRepository.findByLowestPriceGreaterThanEqualAndHighestPriceLessThanEqualOrderByLowestPriceAsc(min, max, pageable);
			}else {
				shopPage = shopRepository.findByLowestPriceGreaterThanEqualAndHighestPriceLessThanEqualOrderByCreatedAtDesc(min, max, pageable);
			}
		}else {
			//全件検索の並び替え分岐
			if (order != null && order.equals("lowestPriceAsc")) {
				shopPage = shopRepository.findAllByOrderByLowestPriceAsc(pageable);
			}else {
				shopPage = shopRepository.findAllByOrderByCreatedAtDesc(pageable);
			}
		}
		
		model.addAttribute("shopPage", shopPage);
		model.addAttribute("keyword", keyword);
		model.addAttribute("lowestPrice", lowestPrice);
		model.addAttribute("highestPrice", highestPrice);
		model.addAttribute("order", order);
		
		model.addAttribute("categoryId", categoryId);
		model.addAttribute("categories", categoryRepository.findAllByOrderByCreatedAtAsc());
		
		return "shops/index";
	}
	
	@GetMapping("/{id}")
	public String show(@PathVariable(name = "id") Integer id,
					   @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
					   Model model) 
	{
		Shop shop = shopRepository.getReferenceById(id);
		List<Review> reviewList = reviewRepository.findTop6ByShopOrderByCreatedAtDesc(shop);
		
		model.addAttribute("shop", shop);
		model.addAttribute("reservationInputForm", new ReservationInputForm());
		
		// ログイン中のユーザーがすでに投稿済みか判定
		boolean hasUserReviewed = false;
		Favorite favorite = null;
		boolean isFavorite = false;
		
		if (userDetailsImpl != null) {
			User user = userDetailsImpl.getUser();
			if (reviewRepository.findFirstByShopAndUser(shop, user) != null) {
				hasUserReviewed = true;
			}
			
			// お気に入り登録済みか判定し、登録データがあれば取得
			favorite = favoriteRepository.findByShopAndUser(shop, user);
			if (favorite != null) {
				isFavorite = true;
			}
		}
		model.addAttribute("shop", shop);
		model.addAttribute("reservationInputForm", new ReservationInputForm());
		model.addAttribute("reviewList", reviewList);
		model.addAttribute("hasUserReviewed", hasUserReviewed);
		model.addAttribute("favorite", favorite);
		model.addAttribute("isFavorite", isFavorite);
		
		return "shops/show";
	}
}
