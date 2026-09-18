package com.example.nagoyameshi.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.UserEditForm;
import com.example.nagoyameshi.repository.UserRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;    
    
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;        
    }    
    
    @GetMapping
    public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {         
        User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());  
        
        model.addAttribute("user", user);
        
        return "user/index";
    }
    
    @GetMapping("/edit")
    public String edit(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {
    	User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId()); 
    	UserEditForm userEditForm = new UserEditForm(user.getId(), user.getName(), user.getFurigana(), user.getEmail());
    	
    	model.addAttribute("userEditForm", userEditForm);
    	
    	return "user/edit";
    }
}
