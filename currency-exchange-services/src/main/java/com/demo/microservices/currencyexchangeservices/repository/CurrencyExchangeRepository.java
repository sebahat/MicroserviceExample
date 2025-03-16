package com.demo.microservices.currencyexchangeservices.repository;

import com.demo.microservices.currencyexchangeservices.dto.CurrencyExchange;


import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange,Long> {

    CurrencyExchange findByFromAndTo(String from ,String to);
}
