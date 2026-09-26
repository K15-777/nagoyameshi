package com.example.nagoyameshi.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.UserService;

@Controller
@RequestMapping("/subscription")
public class SubscriptionController {
	private final UserService userService;
	
	public SubscriptionController(UserService userService) {
		this.userService = userService;
	}
	
	// 有料プラン登録画面の表示
	@GetMapping("/register")
	public String register(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {
		User user = userDetailsImpl.getUser();
		model.addAttribute("user", user);
		
		return "subscription/register";
	}
	
	@PostMapping("/create")
	public String create(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, RedirectAttributes redirectAttributes) {
		User user = userDetailsImpl.getUser();
		userService.registerSubscriber(user);
		
		redirectAttributes.addFlashAttribute("message", "有料プランに登録しました。");
		return "redirect:/user";
	}
	
	
	// 有料プラン解約画面の表示
	@GetMapping("/cancel")
	public String cancel(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {
		User user = userDetailsImpl.getUser();
		model.addAttribute("user", user);
		
		return "subscription/cancel";
	}
	
	@PostMapping("/delete")
	public String delete(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, RedirectAttributes redirectAttributes) {
		User user = userDetailsImpl.getUser();
		userService.cancelSubscriber(user);
		
		redirectAttributes.addFlashAttribute("message", "有料プランを解約しました。");
		return "redirect:/user";
	}
}
