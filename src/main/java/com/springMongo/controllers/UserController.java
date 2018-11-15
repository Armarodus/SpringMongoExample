package com.springMongo.controllers;

import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springMongo.models.User;
import com.springMongo.services.OrderService;
import com.springMongo.services.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private OrderService orderService;

	@GetMapping("/")
	public String home() {
		return "home";
	}

	@GetMapping("/user")
	public String index(Model model) {
		model.addAttribute("users", userService.getAll());
		return "user_list";
	}

	@GetMapping("/user/{id}/delete")
	public String delete(@PathVariable("id") ObjectId id, RedirectAttributes redirect) {
		userService.remove(id);
		redirect.addFlashAttribute("success", new StringBuilder().append("Row deleted ").append(id).toString());
		return "redirect:/user";
	}

	@GetMapping("/user/add")
	public String add(Model model) {
		model.addAttribute("user", new User());
		return "user_form";
	}

	@GetMapping("/user/{id}/edit")
	public String edit(@PathVariable("id") ObjectId id, Model model) {
		model.addAttribute("user", userService.getById(id));
		return "user_form";
	}

	@GetMapping("/user/{id}/orders")
	public String getOrders(@PathVariable("id") ObjectId id, Model model) {
		model.addAttribute("user", userService.getById(id));
		model.addAttribute("orders", orderService.getUserOrders(id));
		model.addAttribute("user_total", orderService.getOrdersSumTotalByUser(id));
		return "user_orders";
	}

	@PostMapping("/user/save")
	public String saveEdited(@Valid User user, Model model, RedirectAttributes redirect) {
		userService.save(user);
		redirect.addFlashAttribute("success", "Saved");
		return "redirect:/user";
	}

}
