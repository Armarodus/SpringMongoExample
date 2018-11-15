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

import com.springMongo.models.Order;
import com.springMongo.services.BookService;
import com.springMongo.services.OrderService;
import com.springMongo.services.UserService;

@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private BookService bookService;
	@Autowired
	private UserService userService;

	@GetMapping("/order")
	public String index(Model model) {
		model.addAttribute("orders", orderService.getAllModified());
		model.addAttribute("sum_total", orderService.getOrdersSumTotal());
		return "order_list";
	}

	@GetMapping("/order/{id}/delete")
	public String delete(@PathVariable("id") ObjectId id, RedirectAttributes redirect) {
		orderService.remove(id);
		redirect.addFlashAttribute("success", new StringBuilder().append("Deleted ").append(id).toString());
		return "redirect:/order";
	}

	@GetMapping("/order/add")
	public String add(Model model) {
		model.addAttribute("order", new Order());
		model.addAttribute("books", bookService.getAll());
		model.addAttribute("users", userService.getAll());
		return "order_form";
	}

	@GetMapping("/order/{id}/edit")
	public String edit(@PathVariable("id") ObjectId id, Model model) {
		model.addAttribute("order", orderService.getById(id));
		model.addAttribute("books", bookService.getAll());
		model.addAttribute("users", userService.getAll());
		return "order_form";
	}

	@PostMapping("/order/save")
	public String saveEdited(@Valid Order order, Model model, RedirectAttributes redirect) {
		orderService.save(order);
		redirect.addFlashAttribute("success", "Збережено");
		return "redirect:/order";
	}
}
