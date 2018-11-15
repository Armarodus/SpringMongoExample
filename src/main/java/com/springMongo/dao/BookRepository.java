package com.springMongo.dao;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.springMongo.models.Book;

public interface BookRepository extends MongoRepository<Book, ObjectId> {

}
