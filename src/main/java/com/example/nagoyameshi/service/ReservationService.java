package com.example.nagoyameshi.service;

import java.time.LocalTime;

import org.springframework.stereotype.Service;

@Service
public class ReservationService {
	// 宿泊人数が定員以下かどうかをチェックする
	public boolean isWithinSeatingCapacity(Integer numberOfPeople, Integer seatingCapacity) {
		return numberOfPeople <= seatingCapacity;
	}
	
	// 予約時間が営業時間内かどうかをチェックする
	public boolean isWithinOperatingHours(LocalTime fromCheckinTime, LocalTime openingTime, LocalTime closingTime) {
		return !fromCheckinTime.isBefore(openingTime) && !fromCheckinTime.isAfter(closingTime);
	}
}
