package com.example.nagoyameshi.service;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Reservation;
import com.example.nagoyameshi.entity.Shop;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReservationRegisterForm;
import com.example.nagoyameshi.repository.ReservationRepository;
import com.example.nagoyameshi.repository.ShopRepository;
import com.example.nagoyameshi.repository.UserRepository;

@Service
public class ReservationService {
	private final ReservationRepository reservationRepository;
	private final ShopRepository shopRepository;
	private final UserRepository userRepository;
	
	public ReservationService(ReservationRepository reservationRepository, ShopRepository shopRepository, UserRepository userRepository) {
		this.reservationRepository = reservationRepository;
		this.shopRepository = shopRepository;
		this.userRepository = userRepository;
	}
	
	@Transactional
	public void create(ReservationRegisterForm reservationRegisterForm) {
		Reservation reservation = new Reservation();
		Shop shop = shopRepository.getReferenceById(reservationRegisterForm.getShopId());
		User user = userRepository.getReferenceById(reservationRegisterForm.getUserId());
		LocalDate reservationDate = reservationRegisterForm.getReservationDate();
		LocalTime fromCheckinTime = reservationRegisterForm.getFromCheckinTime();
		Integer numberOfPeople = reservationRegisterForm.getNumberOfPeople();
		
		reservation.setShop(shop);
		reservation.setUser(user);
		reservation.setReservationDate(reservationDate);
		reservation.setFromCheckinTime(fromCheckinTime);
		reservation.setNumberOfPeople(numberOfPeople);
		
		reservationRepository.save(reservation);
	}
	
	@Transactional
	public void delete(Integer reservationId) {
		reservationRepository.deleteById(reservationId);
	}
	
	// 予約人数が定員以下かどうかをチェックする
	public boolean isWithinSeatingCapacity(Integer numberOfPeople, Integer seatingCapacity) {
		return numberOfPeople <= seatingCapacity;
	}
	
	// 予約時間が営業時間内かどうかをチェックする（深夜跨ぎ対応）
	public boolean isWithinOperatingHours(LocalTime fromCheckinTime, LocalTime openingTime, LocalTime closingTime) {
		if(fromCheckinTime == null || openingTime == null || closingTime == null) {
			return false;
		}
		
		// 同時刻（開店 == 閉店）は24時間営業とみなす（必要がなければ別扱い）
	    if (openingTime.equals(closingTime)) {
	        return true;
	    }
	    
	    // 通常（同日内）: 開 <= 閉 の場合
	    if (!closingTime.isBefore(openingTime)) {
	        return !fromCheckinTime.isBefore(openingTime) && !fromCheckinTime.isAfter(closingTime);
	    }
	    
	    // 深夜跨ぎ（例: 開 18:00, 閉 04:00）: 有効時間は [開店, 23:59..] または [00:00, 閉店]
	    return !fromCheckinTime.isBefore(openingTime) || !fromCheckinTime.isAfter(closingTime);
	}
}
