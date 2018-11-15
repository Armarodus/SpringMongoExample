package com.springMongo.models.dto;

import java.util.Date;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
	private ObjectId id;
	private ObjectId book_id;
	private String book_name;
	private ObjectId user_id;
	private String user_name;
	private Integer count;
	private Double price;
	private Double total;
	private Date date;
}
