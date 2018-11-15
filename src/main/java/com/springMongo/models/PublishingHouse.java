package com.springMongo.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Document(collection = "phouses")
public class PublishingHouse {

	@Id
	private ObjectId id;
	private String name;
	private String owner;
	
	public PublishingHouse() {
		this.id = ObjectId.get();
	}
}
