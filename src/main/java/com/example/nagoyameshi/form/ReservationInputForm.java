package com.example.nagoyameshi.form;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ReservationInputForm {
	@NotNull(message = "日にちを入力してください。")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate reservationDate;
	
	@NotNull(message = "時間を入力してください。")
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime fromCheckinTime;
	
	@NotNull(message = "人数を入力してください。")
	@Min(value = 1, message = "人数は1人以上で入力してください。")
	private Integer numberOfPeople;
	
}
