package com.example.nagoyameshi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.Shop;
import com.example.nagoyameshi.form.ShopEditForm;
import com.example.nagoyameshi.form.ShopRegisterForm;
import com.example.nagoyameshi.repository.CategoryRepository;
import com.example.nagoyameshi.repository.ShopRepository;
import com.example.nagoyameshi.security.AdminDetailsImpl;
import com.example.nagoyameshi.service.ShopService;

@Controller
@RequestMapping("/admin/shops")
public class AdminShopController {
	private final ShopRepository shopRepository;
	private final ShopService shopService;
	private final CategoryRepository categoryRepository;
	
	public AdminShopController (ShopRepository shopRepository, ShopService shopService, CategoryRepository categoryRepository) {
		this.shopRepository = shopRepository;
		this.shopService = shopService;
		this.categoryRepository = categoryRepository;
	}

	@GetMapping
	public String index(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable, @RequestParam(name = "keyword", required = false) String keyword) {
	Page<Shop> shopPage;
	
	if(keyword != null && !keyword.isEmpty()) {
		shopPage = shopRepository.findByNameLike("%" + keyword + "%", pageable);
	}else {
		shopPage = shopRepository.findAll(pageable);
	}
	
	model.addAttribute("shopPage", shopPage);
	model.addAttribute("keyword", keyword);
	
	return "admin/shops/index";
	}
	
	@GetMapping("/{id}")
	public String show(@PathVariable(name = "id") Integer id, Model model) {
		Shop shop = shopRepository.getReferenceById(id);
		
		model.addAttribute("shop", shop);
		
		return "admin/shops/show";
	}
	
	
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("shopRegisterForm", new ShopRegisterForm());
		model.addAttribute("categories", categoryRepository.findAllByOrderByCreatedAtAsc());
		return "admin/shops/register";
	}
	
    @PostMapping("/create")
    public String create(@ModelAttribute @Validated ShopRegisterForm shopRegisterForm,
    					 BindingResult bindingResult, 
    					 RedirectAttributes redirectAttributes, 
    					 @AuthenticationPrincipal AdminDetailsImpl adminDetails,
    					 Model model) {        
        if (bindingResult.hasErrors()) {
        	model.addAttribute("categories", categoryRepository.findAllByOrderByCreatedAtAsc());
            return "admin/shops/register";
        }
        
        Category category = categoryRepository.getReferenceById(shopRegisterForm.getCategoryId());
        
        shopService.create(shopRegisterForm, adminDetails.getAdmin(), category);
        redirectAttributes.addFlashAttribute("successMessage", "店舗を登録しました。");    
        
        return "redirect:/admin/shops";
    }    
    
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable(name = "id") Integer id, Model model) {
    	Shop shop = shopRepository.getReferenceById(id);
    	String imageName = shop.getImageName();
    	ShopEditForm shopEditForm = new ShopEditForm(shop.getId(), shop.getName(), null, shop.getAddress(), shop.getDescription(), shop.getOpeningTime(), shop.getClosingTime(), shop.getLowestPrice(), shop.getHighestPrice(), shop.getSeatingCapacity());
    	
    	model.addAttribute("imageName", imageName );
    	model.addAttribute("shopEditForm", shopEditForm);
    	
    	return "admin/shops/edit";
    }
    
    @PostMapping("/{id}/update")
    public String update(@ModelAttribute @Validated ShopEditForm shopEditForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {        
        if (bindingResult.hasErrors()) {
            return "admin/shops/edit";
        }
        
        shopService.update(shopEditForm);
        redirectAttributes.addFlashAttribute("successMessage", "店舗情報を編集しました。");
        
        return "redirect:/admin/shops";
    }    
    
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
    	shopRepository.deleteById(id);
    	
    	redirectAttributes.addFlashAttribute("successMessage", "店舗を削除しました。");
    	
    	return "redirect:/admin/shops";
    }
}