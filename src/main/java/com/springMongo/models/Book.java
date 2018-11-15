package com.springMongo.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Document(collection = "books")
public class Book {

	@Id
	private ObjectId id;
	private String name;
	private String author;
	private ObjectId ph_id;
	private Double price;

	public Book() {
		this.id = ObjectId.get();
	}
}
