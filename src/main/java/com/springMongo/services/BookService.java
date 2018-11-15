package com.springMongo.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springMongo.dao.BookRepository;
import com.springMongo.dao.PublishingHouseRepository;
import com.springMongo.interfaces.SaveInterface;
import com.springMongo.models.Book;
import com.springMongo.models.PublishingHouse;
import com.springMongo.models.dto.BookDto;

@Service
public class BookService implements SaveInterface {

	private static final String XLSX_TEMPLATE = "Book_template.xlsx";
	private static final String XLSX_OUTPUT = "book_output.xlsx";
	private static final String XLSX_TEMPLATE_MODIFIED = "Book_modified_template.xlsx";
	private static final String XLSX_OUTPUT_MODIFIED = "book_modified_output.xlsx";
	private static final String CONTEXT_XLSX_VAR = "books";

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private PublishingHouseRepository houseRepository;

	private BookDto bookToBookDto(ObjectId book_id) {
		BookDto bookDto = new BookDto();
		Book book = bookRepository.findById(book_id).get();
		bookDto.setId(book.getId());
		bookDto.setAuthor(book.getAuthor());
		bookDto.setName(book.getName());
		bookDto.setPrice(book.getPrice());
		PublishingHouse ph = houseRepository.findById(book.getPh_id()).get();
		bookDto.setPh(ph);
		return bookDto;
	}

	public void save(Book book) {
		bookRepository.save(book);
	}

	public void remove(ObjectId id) {
		bookRepository.deleteById(id);
	}

	public List<Book> getAll() {
		return (List<Book>) bookRepository.findAll();
	}

	public Book getById(ObjectId id) {
		return bookRepository.findById(id).get();
	}

	public List<BookDto> getAllModified() {
		List<BookDto> booksModified = new ArrayList<>();
		List<Book> books = bookRepository.findAll();
		books.forEach(book -> {
			booksModified.add(this.bookToBookDto(book.getId()));
		});
		return booksModified;
	}

	public void saveToExcelBookReport() {
		List<Book> books = bookRepository.findAll();
		try {
			InputStream is = new FileInputStream(new File(XLSX_TEMPLATE));

			OutputStream os = new FileOutputStream(XLSX_OUTPUT);
			Context context = new Context();
			context.putVar(CONTEXT_XLSX_VAR, books);
			JxlsHelper.getInstance().processTemplate(is, os, context);

		} catch (IOException e) {
			e.printStackTrace();
		}
		List<BookDto> booksModified = this.getAllModified();
		try {
			InputStream is = new FileInputStream(new File(XLSX_TEMPLATE_MODIFIED));

			OutputStream os = new FileOutputStream(XLSX_OUTPUT_MODIFIED);
			Context context = new Context();
			context.putVar(CONTEXT_XLSX_VAR, booksModified);
			JxlsHelper.getInstance().processTemplate(is, os, context);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
