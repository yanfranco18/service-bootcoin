package com.bootcoin.service.services;

import com.bootcoin.service.model.Bootcoin;
import com.bootcoin.service.repository.IBootcoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BootcoinServiceImpl implements IBootcoinService{

    private final IBootcoinRepository repository;

    @Override
    public Mono<Bootcoin> create(Bootcoin bootcoin) {
        return repository.save(bootcoin);
    }

    @Override
    public Flux<Bootcoin> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Bootcoin> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Mono<Bootcoin> update(Bootcoin bootcoin) {
        return repository.save(bootcoin);
    }

    @Override
    public Mono<Void> delete(String id) {
        return repository.deleteById(id);
    }
}
