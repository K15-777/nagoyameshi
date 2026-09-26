package com.example.nagoyameshi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.entity.Shop;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReviewEditForm;
import com.example.nagoyameshi.form.ReviewRegisterForm;
import com.example.nagoyameshi.repository.ReviewRepository;
import com.example.nagoyameshi.repository.ShopRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.ReviewService;

@Controller
@RequestMapping("shops/{shopId}/reviews")
public class ReviewController {
	private final ShopRepository shopRepository;
	private final ReviewService reviewService;
	private final ReviewRepository reviewRepository;

	public ReviewController(ShopRepository shopRepository, ReviewService reviewService,
			ReviewRepository reviewRepository) {
		this.shopRepository = shopRepository;
		this.reviewService = reviewService;
		this.reviewRepository = reviewRepository;
	}

	@GetMapping("/register")
	public String register(@PathVariable(name = "shopId") Integer shopId, Model model) {
		Shop shop = shopRepository.getReferenceById(shopId);

		model.addAttribute("shop", shop);
		model.addAttribute("reviewRegisterForm", new ReviewRegisterForm());

		return "review/register";
	}

	@PostMapping("/create")
	public String create(@PathVariable(name = "shopId") Integer shopId,
			@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
			@ModelAttribute @Validated ReviewRegisterForm reviewRegisterForm,
			BindingResult bindingResult,
			RedirectAttributes redirectAttributes,
			Model model) {
		Shop shop = shopRepository.getReferenceById(shopId);
		User user = userDetailsImpl.getUser();

		if (bindingResult.hasErrors()) {
			model.addAttribute("shop", shop);
			return "review/register";
		}

		reviewService.create(shop, user, reviewRegisterForm);
		redirectAttributes.addFlashAttribute("successMessage", "レビューを投稿しました。");

		return "redirect:/shops/" + shopId;
	}

	@GetMapping("/index")
	public String index(@PathVariable(name = "shopId") Integer shopId,
			@PageableDefault(size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
			Model model) {
		Shop shop = shopRepository.getReferenceById(shopId);
		Page<Review> reviewPage = reviewRepository.findByShopOrderByCreatedAtDesc(shop, pageable);

		model.addAttribute("shop", shop);
		model.addAttribute("rebiewPage", reviewPage);

		return "review/index";
	}

	@GetMapping("/{reviewId}/edit")
	public String edit(@PathVariable(name = "shopId") Integer shopId,
			@PathVariable(name = "reviewId") Integer reviewId,
			Model model) {
		Shop shop = shopRepository.getReferenceById(shopId);
		Review review = reviewRepository.getReferenceById(reviewId);
		ReviewEditForm reviewEditForm = new ReviewEditForm(review.getId(), review.getRating(),review.getComment());

		model.addAttribute("shop", shop);
		model.addAttribute("review", review);
		model.addAttribute("reviewEditForm", reviewEditForm);

		return "review/edit";
	}
	
	//更新処理
	@PostMapping("/{reviewId}/update")
	public String update(@PathVariable(name = "shopId") Integer shopId,
			             @PathVariable(name = "reviewId") Integer reviewId,
			             @ModelAttribute @Validated ReviewEditForm reviewEditForm,
			             BindingResult bindingResult,
			             RedirectAttributes redirectAttributes,
			             Model model) 
	{
		Shop shop = shopRepository.getReferenceById(shopId);
		Review review = reviewRepository.getReferenceById(reviewId);
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("shop", shop);
			model.addAttribute("review", review);
			return "review/edit";
		}
		
		reviewService.update(reviewEditForm);
		redirectAttributes.addFlashAttribute("successMessage", "レビューを編集しました。");
		
		return "redirect:/shops/" + shopId;
	}
	
	//3. 削除処理
	@PostMapping("/{reviewId}/delete")
	public String delete(@PathVariable(name = "shopId") Integer shopId,
						 @PathVariable(name = "reviewId") Integer reviewId,
						 RedirectAttributes redirectAttributes)
	{
		Review review = reviewRepository.getReferenceById(reviewId);
		reviewService.delete(review);
		redirectAttributes.addFlashAttribute("successMessage", "レビューを削除しました。");
		
		return "redirect:/shops/" + shopId;
	}

}
