package com.example.nagoyameshi.form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class ReviewRegisterForm {
	@NotNull(message = "評価を入力してください。")
	@Min(value = 1, message = "評価は1～5のいずれかを選択してください。")
	@Max(value = 5, message = "評価は1～5のいずれかを選択してください。")
	private Integer rating;
	
	@NotBlank(message = "コメントを入力してください。")
	@Size(max = 255, message = "256文字以内で入力してください。")
	private String comment;
}
