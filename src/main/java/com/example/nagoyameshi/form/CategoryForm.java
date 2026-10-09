package com.example.nagoyameshi.form;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CategoryForm {
	private Integer Id;
	
	@NotBlank(message = "カテゴリ名を入力してください")
	private String name;
}
