package com.letrogthien.transaction.repositories;

import com.letrogthien.transaction.exceptions.ErrorLog;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ErrorLogRepository extends MongoRepository<ErrorLog, String> {

}
