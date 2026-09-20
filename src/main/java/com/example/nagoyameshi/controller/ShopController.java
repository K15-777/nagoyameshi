package com.example.nagoyameshi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.nagoyameshi.entity.Shop;
import com.example.nagoyameshi.repository.ShopRepository;

@Controller
@RequestMapping("/shops")
public class ShopController {
	private final ShopRepository shopRepository;
	
	public ShopController(ShopRepository shopRepository) {
		this.shopRepository = shopRepository; 
	}
	
	@GetMapping
	public String index(@RequestParam(name = "keyword", required = false) String keyword,
						@RequestParam(name = "lowestPrice", required = false) Integer lowestPrice,
						@RequestParam(name = "highestPrice", required = false) Integer highestPrice,
						@RequestParam(name = "order", required = false) String order,
						@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
						Model model)
	{
		Page<Shop> shopPage;
		
		if(keyword != null && !keyword.isEmpty()) {
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
		
		return "shops/index";
	}
	
	@GetMapping("/{id}")
	public String show(@PathVariable(name = "id") Integer id, Model model) {
		Shop shop = shopRepository.getReferenceById(id);
		
		model.addAttribute("shop", shop);
		
		return "shops/show";
	}
}
