package com.example.nagoyameshi.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.UserEditForm;
import com.example.nagoyameshi.repository.UserRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.security.UserDetailsServiceImpl;
import com.example.nagoyameshi.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
    
    public UserController(UserRepository userRepository, UserService userService, UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }    
    
    @GetMapping
    public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model,
            HttpServletRequest request, HttpServletResponse response) {         
        User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());  
        
        // Stripeの決済完了/解約直後は、ログインセッション内のsubscriber情報が古いままになっているため、
        // DBの最新情報でSecurityContextの認証情報を作り直す（画面表示を即時に正しく反映させるため）
        refreshAuthentication(user.getEmail(), request, response);
        
        model.addAttribute("user", user);
        
        return "user/index";
    }
    
    // ログインセッションの認証情報（Authentication）をDBの最新のUser情報で作り直す
    // Spring Security 6以降はrequireExplicitSaveがデフォルトで有効なため、
    // SecurityContextHolderへの設定だけではHTTPセッションに反映されない。
    // そのためSecurityContextRepositoryへ明示的に保存する必要がある。
    private void refreshAuthentication(String email, HttpServletRequest request, HttpServletResponse response) {
        UserDetails refreshedUserDetails = userDetailsServiceImpl.loadUserByUsername(email);
        
        UsernamePasswordAuthenticationToken newAuthentication = new UsernamePasswordAuthenticationToken(
                refreshedUserDetails, null, refreshedUserDetails.getAuthorities());
        
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(newAuthentication);
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context, request, response);
    }
    
    @GetMapping("/edit")
    public String edit(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {
    	User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId()); 
    	UserEditForm userEditForm = new UserEditForm(user.getId(), user.getName(), user.getFurigana(), user.getEmail());
    	
    	model.addAttribute("userEditForm", userEditForm);
    	
    	return "user/edit";
    }
    
    @PostMapping("/update")
    public String update(@ModelAttribute @Validated UserEditForm userEditForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
    	// メールアドレスが変更されており、かつ登録済みであれば、BindingResultオブジェクトにエラー内容を追加する
    	if (userService.isEmailChanged(userEditForm) && userService.isEmailRegistered(userEditForm.getEmail())) {
    		FieldError fieldError = new FieldError(bindingResult.getObjectName(), "email", "すでに登録済みのメールアドレスです。");
    		bindingResult.addError(fieldError);
    	}
    	
    	if (bindingResult.hasErrors()) {
    		return "user/edit";
    	}
    	
    	userService.update(userEditForm);
    	redirectAttributes.addFlashAttribute("successMessage", "会員情報を編集しました。");
    	
    	return "redirect:/user";
    }
}
