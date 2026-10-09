package com.example.nagoyameshi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.entity.Shop;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReviewEditForm;
import com.example.nagoyameshi.form.ReviewRegisterForm;
import com.example.nagoyameshi.repository.ReviewRepository;

@Service
public class ReviewService {
	private final ReviewRepository reviewRepository;
	
	public ReviewService(ReviewRepository reviewRepository) {
		this.reviewRepository = reviewRepository;
	}
	
	@Transactional
	public void create(Shop shop, User user, ReviewRegisterForm reviewRegisterForm) {
		Review review = new Review();
		
		review.setShop(shop);
		review.setUser(user);
		review.setRating(reviewRegisterForm.getRating());
		review.setComment(reviewRegisterForm.getComment());
		
		reviewRepository.save(review);
	}
	
	@Transactional
	public void update(ReviewEditForm reviewEditForm) {
		Review review = reviewRepository.getReferenceById(reviewEditForm.getId());
		
		review.setRating(reviewEditForm.getRating());
		review.setComment(reviewEditForm.getComment());
		
		reviewRepository.save(review);
	}
	
	@Transactional
	public void delete(Review review) {
		reviewRepository.delete(review);
	}
}
