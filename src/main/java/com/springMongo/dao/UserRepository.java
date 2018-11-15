package com.springMongo.dao;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.springMongo.models.User;

public interface UserRepository extends MongoRepository<User, ObjectId> {

}
