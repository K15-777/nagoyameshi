package com.example.nagoyameshi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * 管理者ログイン画面を表示するだけのコントローラー。
 * 実際の認証処理（POST /admin/login）はSpring Securityのformログイン機能が処理する。
 */
@Controller
public class AdminLoginController {

	@GetMapping("/admin/login")
	public String login() {
		return "admin/login";
	}
}
