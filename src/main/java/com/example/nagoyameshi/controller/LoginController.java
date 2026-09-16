package com.example.nagoyameshi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * 会員ログイン画面を表示するだけのコントローラー。
 * 実際の認証処理（POST /login）はSpring Securityのformログイン機能が処理する。
 */
@Controller
public class LoginController {

	@GetMapping("/login")
	public String login() {
		System.out.println("login called");
		return "login";
	}
}
