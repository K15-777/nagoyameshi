package com.example.nagoyameshi.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.nagoyameshi.entity.Shop;
import com.example.nagoyameshi.repository.ShopRepository;

@Controller
@RequestMapping("/admin/shops")
public class AdminShopController {
	private final ShopRepository shopRepository;
	
	public AdminShopController (ShopRepository shopRepository) {
		this.shopRepository = shopRepository;
	}

	@GetMapping
	public String index(Model model) {
	List<Shop> shops = shopRepository.findAll();
	
	model.addAttribute("shops", shops);
	
	return "admin/shops/index";
	}
}