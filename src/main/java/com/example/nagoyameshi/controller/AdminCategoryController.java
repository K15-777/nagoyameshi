package com.example.nagoyameshi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.form.CategoryForm;
import com.example.nagoyameshi.repository.CategoryRepository;
import com.example.nagoyameshi.service.CategoryService;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {
	private final CategoryService categoryService;
	private final CategoryRepository categoryRepository;
	
	public AdminCategoryController(CategoryService categoryService, CategoryRepository categoryRepository) {
		this.categoryService = categoryService;
		this.categoryRepository = categoryRepository;
	}
	
	@GetMapping
	public String index(@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
						Model model) 
	{
		Page<Category> categoryPage = categoryRepository.findAll(pageable);
		model.addAttribute("categoryPage", categoryPage);
		return "admin/categories/index";
	}
	
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("categoryForm", new CategoryForm());
		return "admin/categories/register";
	}
	
	@PostMapping("/create")
	public String create(@ModelAttribute @Validated CategoryForm categoryForm,
			             BindingResult bindingResult,
			             RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "admin/categories/register";
		}
		
		categoryService.create(categoryForm);
		redirectAttributes.addFlashAttribute("successMessage", "カテゴリを登録しました");
		
		return "redirect:/admin/categories";
	}
	
	@GetMapping("/{id}/edit")
	public String edit(@PathVariable(name = "id") Integer id, Model model) {
		Category category = categoryRepository.getReferenceById(id);

		CategoryForm categoryForm = new CategoryForm();
		categoryForm.setId(category.getId());
		categoryForm.setName(category.getName());

		model.addAttribute("categoryForm", categoryForm);

		return "admin/categories/edit";
	}
	
	@PostMapping("/{id}/update")
	public String update(@ModelAttribute @Validated CategoryForm categoryForm,
			BindingResult bindingResult,
			RedirectAttributes redirectAttributes) 
	{
		if (bindingResult.hasErrors()) {
			return "admin/categories/edit";
		}
			
			categoryService.update(categoryForm);
			redirectAttributes.addFlashAttribute("successMessage", "カテゴリを編集しました。");

			return "redirect:/admin/categories";
		}
	
	@PostMapping("/{id}/delete")
	public String delete(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
		categoryService.delete(id);
		redirectAttributes.addFlashAttribute("successMessage", "カテゴリを削除しました。");

		return "redirect:/admin/categories";
	}
		
	
}

