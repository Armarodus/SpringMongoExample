package com.springMongo.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.bson.types.ObjectId;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springMongo.dao.UserRepository;
import com.springMongo.interfaces.SaveInterface;
import com.springMongo.models.User;

@Service
public class UserService implements SaveInterface{

	private static final String XLSX_TEMPLATE = "User_template.xlsx";
	private static final String XLSX_OUTPUT = "user_output.xlsx";
	private static final String CONTEXT_XLSX_VAR="users";
	
	@Autowired
	private UserRepository userRepository;

	public void save(User user) {
		userRepository.save(user);
	}

	public void remove(ObjectId id) {
		userRepository.deleteById(id);
	}

	public List<User> getAll() {
		return userRepository.findAll();
	}

	public User getById(ObjectId id) {
		return userRepository.findById(id).get();
	}

	public void saveToExcelBookReport() {
		List<User> users = this.getAll();
		try {
			InputStream is = new FileInputStream(new File(XLSX_TEMPLATE));

			OutputStream os = new FileOutputStream(XLSX_OUTPUT);
			Context context = new Context();
			context.putVar(CONTEXT_XLSX_VAR, users);
			JxlsHelper.getInstance().processTemplate(is, os, context);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
