package com.bootcoin.service.controller;

import com.bootcoin.service.model.Bootcoin;
import com.bootcoin.service.services.IBootcoinService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.LocalDate;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/bootcoins")
public class BootcoinController {

    private final IBootcoinService service;

    @CircuitBreaker(name="bootcoinservice", fallbackMethod = "fallback")
    @TimeLimiter(name="bootcoinservice")
    @GetMapping
    public Mono<ResponseEntity<Flux<Bootcoin>>> getBootcoin(){
        log.info("Starting the list");
        return Mono.just(
                ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(service.findAll()));
    }

    @CircuitBreaker(name="bootcoinservice", fallbackMethod = "fallback")
    @TimeLimiter(name="bootcoinservice")
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteBootcoin (@PathVariable String id){

        return service.delete(id)
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }

    @CircuitBreaker(name="bootcoinservice", fallbackMethod = "fallback")
    @TimeLimiter(name="bootcoinservice")
    @PostMapping
    public Mono<ResponseEntity<Bootcoin>> saveBootcoin (@RequestBody Bootcoin bootcoin){
        log.info("Validating date");
        if(bootcoin.getCreateDate()==null){
            bootcoin.setCreateDate(LocalDate.now());
        }
        return service.create(bootcoin)
                .map(p -> ResponseEntity.created(URI.create("/bootcoins/".concat(p.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p));
    }

    @CircuitBreaker(name="bootcoinservice", fallbackMethod = "fallback")
    @TimeLimiter(name="bootcoinservice")
    @GetMapping("/getById/{id}")
    public Mono<ResponseEntity<Bootcoin>> getById(@PathVariable String id){
        log.info("Validating Id before creating!");
        return service.findById(id)
                .map(p -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @CircuitBreaker(name="bootcoinservice", fallbackMethod = "fallback")
    @TimeLimiter(name="bootcoinservice")
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Bootcoin>> editProduct (@RequestBody Bootcoin bootcoin, @PathVariable String id){
        log.info("Validating Id before update!");
        return service.findById(id)
                .flatMap(b ->{
                    b.setTasaCompra(bootcoin.getTasaCompra());
                    b.setTasaVenta(bootcoin.getTasaVenta());
                    b.setTipoMoneda(bootcoin.getTipoMoneda());
                    return service.update(b);
                })
                .map(bo -> ResponseEntity.created(URI.create("/bootcoins/".concat(bo.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(bo))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    //metodo para manejar el error
    private String fallback(HttpServerErrorException ex) {
        return "Response 200, fallback method for error:  " + ex.getMessage();
    }


}
