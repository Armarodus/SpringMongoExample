package com.springMongo.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Document(collection = "users")
public class User {
	@Id
	private ObjectId id;
	private String name;
	private String lastName;
	
	public User() {
		this.id = ObjectId.get();
	}
}
