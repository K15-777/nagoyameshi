package com.example.nagoyameshi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nagoyameshi.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
	public Admin findByEmail(String email);
}
