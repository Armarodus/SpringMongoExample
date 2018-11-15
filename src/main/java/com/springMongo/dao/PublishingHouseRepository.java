package com.springMongo.dao;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.springMongo.models.PublishingHouse;

public interface PublishingHouseRepository extends MongoRepository<PublishingHouse, ObjectId> {

}
