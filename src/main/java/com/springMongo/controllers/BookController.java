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

import com.springMongo.models.Book;
import com.springMongo.services.BookService;
import com.springMongo.services.PublishingHouseService;

@Controller
public class BookController {

	@Autowired
	private BookService bookService;

	@Autowired
	private PublishingHouseService houseService;

	@GetMapping("/book")
	public String getBooks(Model model) {
		model.addAttribute("books", bookService.getAll());
		return "book_list";
	}

	@GetMapping("/bookmodified")
	public String getBooksModified(Model model) {
		model.addAttribute("books", bookService.getAllModified());
		return "book_modified_list";
	}

	@GetMapping("/book/{id}/delete")
	public String delete(@PathVariable("id") ObjectId id, RedirectAttributes redirect) {
		bookService.remove(id);
		;
		redirect.addFlashAttribute("success", new StringBuilder().append("Deleted ").append(id).toString());
		return "redirect:/book";
	}

	@GetMapping("/book/add")
	public String add(Model model) {
		model.addAttribute("book", new Book());
		model.addAttribute("phouses", houseService.getAll());
		return "book_form";
	}

	@GetMapping("/book/{id}/edit")
	public String edit(@PathVariable("id") ObjectId id, Model model) {
		model.addAttribute("book", bookService.getById(id));
		model.addAttribute("phouses", houseService.getAll());
		return "book_form";
	}

	@PostMapping(value = "/book/save")
	public String saveEdited(@Valid Book book, Model model, RedirectAttributes redirect) {
		bookService.save(book);
		redirect.addFlashAttribute("success", "Збережено");
		return "redirect:/book";
	}

}
