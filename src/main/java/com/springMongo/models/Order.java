package com.springMongo.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Document(collection = "orders")
public class Order {
	@Id
	private ObjectId id;
	private ObjectId book_id;
	private ObjectId user_id;
	private Integer count;
	
	public Order() {
		this.id = ObjectId.get();
	}
}
