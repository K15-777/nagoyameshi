package com.example.nagoyameshi.form;

import java.time.LocalTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShopEditForm {
	@NotNull
	private Integer id;
	
	@NotBlank(message = "店舗名を入力してください。")
	private String name;
	
	private MultipartFile imageFile;
	
	@NotBlank(message = "住所を入力してください。")
	private String address;	
	
	@NotBlank(message = "説明を入力してください。")
	private String description;
	
	@NotNull(message = "営業開始時間を入力してください。")
	private LocalTime openingTime;
	
	@NotNull(message = "営業終了時間を入力してください。")
	private LocalTime closingTime;
	
	@NotNull(message = "下限価格を入力してください。")
	@Min(value = 1, message = "下限価格は1円以上に設定してください。")
	private Integer lowestPrice;
	
	@NotNull(message = "上限価格を入力してください。")
	@Min(value = 1, message = "上限価格は1円以上に設定してください。")
	private Integer highestPrice;
	
	@NotNull(message = "定員を入力してください。")
	@Min(value = 1, message = "定員は1人以上に設定してください。")
	private Integer seatingCapacity;
}
