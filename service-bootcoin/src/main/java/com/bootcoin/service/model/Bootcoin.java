package com.bootcoin.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("Bootcoins")
public class Bootcoin {

    @Id
    private String id;

    private Double  tasaCompra;

    private Double tasaVenta;

    private String tipoMoneda;

    private LocalDate createDate;
}
