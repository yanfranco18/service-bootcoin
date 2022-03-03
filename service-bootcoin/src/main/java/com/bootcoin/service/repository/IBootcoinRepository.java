package com.bootcoin.service.repository;

import com.bootcoin.service.model.Bootcoin;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBootcoinRepository extends ReactiveMongoRepository<Bootcoin, String> {
}
