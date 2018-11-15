package com.springMongo.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springMongo.dao.BookRepository;
import com.springMongo.dao.OrderRepository;
import com.springMongo.dao.UserRepository;
import com.springMongo.interfaces.SaveInterface;
import com.springMongo.models.Book;
import com.springMongo.models.Order;
import com.springMongo.models.User;
import com.springMongo.models.dto.OrderDto;

@Service
public class OrderService implements SaveInterface {

	private static final String XLSX_TEMPLATE = "Order_modified_template.xlsx";
	private static final String XLSX_OUTPUT = "order_output.xlsx";
	private static final String CONTEXT_XLSX_VAR = "orders";
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private Double sum;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private UserRepository userRepository;

	private OrderDto orderToOrderDto(Order order) {
		OrderDto orderDto = new OrderDto();
		orderDto.setId(order.getId());
		orderDto.setBook_id(order.getBook_id());
		orderDto.setUser_id(order.getUser_id());
		orderDto.setCount(order.getCount());
		orderDto.setDate(new Date());
		Book book = bookRepository.findById(order.getBook_id()).get();
		orderDto.setBook_name(book.getName());
		orderDto.setPrice(book.getPrice());
		orderDto.setTotal(order.getCount() * book.getPrice());
		User user = userRepository.findById(order.getUser_id()).get();
		orderDto.setUser_name(user.getName());
		return orderDto;
	}

	public void save(Order order) {
		log.info("message {}", order.toString());
		orderRepository.save(order);
	}

	public void remove(ObjectId id) {
		orderRepository.deleteById(id);
	}

	public List<Order> getAll() {
		return orderRepository.findAll();
	}

	public Order getById(ObjectId id) {
		return orderRepository.findById(id).get();
	}

	public List<OrderDto> getAllModified() {
		List<OrderDto> ordersModified = new ArrayList<>();
		List<Order> orders = orderRepository.findAll();
		orders.forEach(order -> {
			ordersModified.add(orderToOrderDto(order));
		});
		return ordersModified;
	}

	public Double getOrdersSumTotal() {
		sum = 0.0;
		List<Order> orders = orderRepository.findAll();
		orders.forEach(order -> {
			sum = sum + bookRepository.findById(order.getBook_id()).get().getPrice() * order.getCount();
		});
		return sum;
	}

	public Double getOrdersSumTotalByUser(ObjectId user_id) {
		sum = 0.0;
		List<Order> orders = orderRepository.findByUserId(user_id);
		orders.forEach(order -> {
			sum = sum + bookRepository.findById(order.getBook_id()).get().getPrice() * order.getCount();
		});
		return sum;
	}

	public List<OrderDto> getUserOrders(ObjectId user_id) {
		return this.getAllModified().stream().filter(order -> order.getUser_id().equals(user_id))
				.collect(Collectors.toList());
	}

	public void saveToExcelBookReport() {
		List<OrderDto> orders = this.getAllModified();
		try {
			InputStream is = new FileInputStream(new File(XLSX_TEMPLATE));

			OutputStream os = new FileOutputStream(XLSX_OUTPUT);
			Context context = new Context();
			context.putVar(CONTEXT_XLSX_VAR, orders);
			JxlsHelper.getInstance().processTemplate(is, os, context);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
