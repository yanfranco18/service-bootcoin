package com.bootcoin.service.services;

import com.bootcoin.service.model.Bootcoin;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBootcoinService {

    Mono<Bootcoin> create(Bootcoin bootcoin);

    Flux<Bootcoin> findAll();

    Mono<Bootcoin> findById(String id);

    Mono<Bootcoin> update(Bootcoin bootcoin);

    Mono<Void> delete(String id);
}
