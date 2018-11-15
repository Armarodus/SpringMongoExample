package com.springMongo.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.springMongo.services.BookService;
import com.springMongo.services.OrderService;
import com.springMongo.services.PublishingHouseService;
import com.springMongo.services.UserService;

@Controller
public class FileController {

	@Autowired
	private BookService bookService;

	@Autowired
	private UserService userService;

	@Autowired
	private PublishingHouseService houseService;

	@Autowired
	private OrderService orderService;

	@GetMapping(value = "/file")
	public String getFileList() {
		return "files_list";
	}

	@GetMapping(value = "/files/{file_name}")
	public void getFile(@PathVariable("file_name") String fileName, HttpServletResponse response) {
		bookService.saveToExcelBookReport();
		userService.saveToExcelBookReport();
		houseService.saveToExcelBookReport();
		orderService.saveToExcelBookReport();
		try {
			// get your file as InputStream
			InputStream is = new FileInputStream(new File(fileName));
			// copy it to response's OutputStream
			org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();
		} catch (IOException ex) {

			throw new RuntimeException("IOError writing file to output stream");
		}
	}
}
