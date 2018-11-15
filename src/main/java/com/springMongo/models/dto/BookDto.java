package com.springMongo.models.dto;

import org.bson.types.ObjectId;

import com.springMongo.models.PublishingHouse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

	private ObjectId id;
	private String name;
	private String author;
	private PublishingHouse ph;
	private Double price;
}
