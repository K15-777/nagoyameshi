package com.example.nagoyameshi.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ResetPasswordForm;
import com.example.nagoyameshi.repository.UserRepository;

@Controller
public class ResetPasswordController {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public ResetPasswordController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	// パスワード再設定画面の表示
	@GetMapping("/reset-password")
	public String index(Model model) {
		model.addAttribute("resetPasswordForm", new ResetPasswordForm());
		return "auth/reset-password";
	}
	
	// パスワード再設定の実行
	@PostMapping("/reset-password")
	public String reset(@ModelAttribute @Validated ResetPasswordForm resetPasswordForm,
			            BindingResult bindingResult,
			            RedirectAttributes redirectAttributes,
			            Model model) 
	{
		// パスワード一致チェック
		if (!resetPasswordForm.getPassword().equals(resetPasswordForm.getPasswordConfirmation())) {
			FieldError fieldError = new FieldError (bindingResult.getObjectName(), "passwordConfirmation", "パスワードと確認用パスワードが一致しません。");
			bindingResult.addError(fieldError);
		}
		
		// メールアドレスの存在チェック
		User user = userRepository.findByEmail(resetPasswordForm.getEmail());
		if (user == null) {
			FieldError fieldError = new FieldError(bindingResult.getObjectName(), "email", "このメールアドレスは登録されていません。");
			bindingResult.addError(fieldError);
		}
		
		if (bindingResult.hasErrors()) {
			return "auth/reset-password";
		}
		
		user.setPassword(passwordEncoder.encode(resetPasswordForm.getPassword()));
		userRepository.save(user);
		
		redirectAttributes.addFlashAttribute("successMessage", "パスワードを再設定しました。新しいパスワードでログインしてください。");
		return "redirect:/login";
	}
}
