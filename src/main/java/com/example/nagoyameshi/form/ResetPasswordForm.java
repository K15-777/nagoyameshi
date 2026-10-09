package com.example.nagoyameshi.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class ResetPasswordForm {
	@NotBlank(message = "メールアドレスを入力してください。")
	@Email(message = "メールアドレスの形式が正しくありません。")
	private String email;
	
	@NotBlank(message = "新しいパスワードを入力してください。")
	@Length(min = 8, message = "パスワードは8文字以上で入力してください。")
	private String Password;
	
	@NotBlank(message = "新しいパスワード(確認用)を入力してください。")
	private String PasswordConfirmation;
}
