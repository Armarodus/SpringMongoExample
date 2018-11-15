package com.springMongo.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.springMongo.models.Order;

public interface OrderRepository extends MongoRepository<Order, ObjectId> {
	@Query("{ 'user_id' : ?0 }")
	List<Order> findByUserId(ObjectId id);
}
