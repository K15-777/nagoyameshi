package com.example.nagoyameshi.controller;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.StripeService;
import com.example.nagoyameshi.service.UserService;

@Controller
@RequestMapping("/subscription")
public class SubscriptionController {
	private final UserService userService;
	private final StripeService stripeService;

	@Value("${stripe.publishable-key}")
	private String stripePublicKey;

	public SubscriptionController(UserService userService, StripeService stripeService) {
		this.userService = userService;
		this.stripeService = stripeService;
	}

	// 有料プラン登録画面の表示（同時にCheckoutセッションも作成する）
	@GetMapping("/register")
	public String register(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
			HttpServletRequest httpServletRequest,
			Model model) {
		User user = userDetailsImpl.getUser();
		String sessionId = stripeService.createCheckoutSubscriptionSession(user, httpServletRequest);

		model.addAttribute("user", user);
		model.addAttribute("sessionId", sessionId);
		model.addAttribute("stripePublicKey", stripePublicKey);

		return "subscription/register";
	}

	// 有料プラン解約画面の表示
	@GetMapping("/cancel")
	public String cancel(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {
		User user = userDetailsImpl.getUser();
		model.addAttribute("user", user);

		return "subscription/cancel";
	}

	@PostMapping("/delete")
	public String delete(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, RedirectAttributes redirectAttributes) throws Exception {
		User user = userDetailsImpl.getUser();
		stripeService.cancelSubscription(user);

		redirectAttributes.addFlashAttribute("message", "有料プランを解約しました。");
		return "redirect:/user";
	}
}
