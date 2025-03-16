package com.demo.microservices.currencyconversionservices.proxy;
import com.demo.microservices.currencyconversionservices.dto.CurrencyConversion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Using only the service name for the "currency-exchange" service,
// because the port and URL can change dynamically in a microservices architecture.
@FeignClient(name = "CURRENCY-EXCHANGE")
public interface CurrencyExchangeProxy {

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyConversion retriveExchangeValue(@PathVariable String from , @PathVariable String to);

}
