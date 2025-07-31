package com.kce.repository;

import com.kce.entity.Request;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends MongoRepository<Request, String> {

    List<Request> findByRegisterNumber(String registerNumber);
    List<Request> findByStatus(String status);
//    List<Request> findByPriority(String priority); // Fetch by priority


}
