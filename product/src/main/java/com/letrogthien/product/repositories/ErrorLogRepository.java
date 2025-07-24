package com.letrogthien.product.repositories;

import com.letrogthien.product.exceptions.ErrorLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ErrorLogRepository extends MongoRepository<ErrorLog, String> {
    // Custom query methods can be defined here if needed
}
