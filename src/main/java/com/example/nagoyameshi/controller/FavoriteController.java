package com.example.nagoyameshi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Favorite;
import com.example.nagoyameshi.entity.Shop;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.FavoriteRepository;
import com.example.nagoyameshi.repository.ShopRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.FavoriteService;

@Controller
public class FavoriteController {
	private final FavoriteService favoriteService;
	private final FavoriteRepository favoriteRepository;
	private final ShopRepository shopRepository;
	
	public FavoriteController(FavoriteService favoriteService, FavoriteRepository favoriteRepository, ShopRepository shopRepository) {
		this.favoriteService = favoriteService;
		this.favoriteRepository = favoriteRepository;
		this.shopRepository = shopRepository;
	}
	
	// お気に入り一覧画面
	@GetMapping("/favorites")
	public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
						@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
						Model model)
	{
		User user = userDetailsImpl.getUser();
		Page<Favorite> favoritePage = favoriteRepository.findByUserOrderByCreatedAtDesc(user, pageable);
		
		model.addAttribute("favoritePage", favoritePage);
		
		return "favorites/index";
	}
	
	// お気に入り追加
	@PostMapping("/shops/{shopId}/favorites/create")
	public String create(@PathVariable(name = "shopId") Integer shopId,
						 @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
						 RedirectAttributes redirectAttributes)
	{
		Shop shop = shopRepository.getReferenceById(shopId);
		User user = userDetailsImpl.getUser();

		favoriteService.create(shop, user);
		redirectAttributes.addFlashAttribute("successMessage", "お気に入りに追加しました。");
		
		return "redirect:/shops/" + shopId;
	}
	
	
	// お気に入り解除
	@PostMapping("/shops/{shopId}/favorites/{favoriteId}/delete")
	public String delete(@PathVariable(name = "shopId") Integer shopId,
			             @PathVariable(name = "favoriteId") Integer favoriteId,
			             RedirectAttributes redirectAttributes)
	{
		Favorite favorite = favoriteRepository.getReferenceById(favoriteId);
		favoriteService.delete(favorite);
		redirectAttributes.addFlashAttribute("successMessage", "お気に入りから削除しました。");
		
		return "redirect:/shops/" + shopId;
	}
}
