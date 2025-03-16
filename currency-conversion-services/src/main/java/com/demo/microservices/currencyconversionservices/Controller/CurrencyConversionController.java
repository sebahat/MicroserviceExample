package com.demo.microservices.currencyconversionservices.Controller;


import com.demo.microservices.currencyconversionservices.dto.CurrencyConversion;
import com.demo.microservices.currencyconversionservices.proxy.CurrencyExchangeProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import org.springframework.core.env.Environment;
@RestController
public class CurrencyConversionController {
    Logger log= LoggerFactory.getLogger(CurrencyConversionController.class);
    @Autowired
    CurrencyExchangeProxy currencyExchangeProxy;

    @Autowired
    private AmqpAdmin amqpAdmin;
    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(@PathVariable String from ,@PathVariable String to , @PathVariable BigDecimal quantity){
        HashMap<String,String> uriVariables = new HashMap<>();
        uriVariables.put("from",from);
        uriVariables.put("to",to);
        ResponseEntity<CurrencyConversion> responseEntity =  new RestTemplate()
                .getForEntity("http://currency-exchange-service:8089/currency-exchange/from/{from}/to/{to}"
                        ,CurrencyConversion.class,uriVariables);
        CurrencyConversion currencyConversion = responseEntity.getBody();
        return new CurrencyConversion(currencyConversion.getId(), from,to,
                quantity,currencyConversion.getConversionMultiple(),quantity.multiply(currencyConversion.
                getConversionMultiple()));
    }

    //feignclient example controller
    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversionFeign(@PathVariable String from ,@PathVariable String to , @PathVariable BigDecimal quantity){
        CurrencyConversion  currencyConversion =  currencyExchangeProxy.retriveExchangeValue(from,to);
        return new CurrencyConversion(currencyConversion.getId(), from,to,
                quantity,currencyConversion.getConversionMultiple(),quantity.multiply(currencyConversion.
                getConversionMultiple()));
    }

    @GetMapping("/currency-conversion-from-rabbit/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversionFromRabbit(@PathVariable String from ,@PathVariable String to , @PathVariable BigDecimal quantity) {
        // Burada RabbitMQ'dan gelen mesaja göre dönüşüm hesaplamaları yapılacak
        // Parametreli constructor kullanarak nesne oluşturuyoruz
        CurrencyConversion currencyConversion = new CurrencyConversion(
                1L, // id
                from, // from
                to, // to
                quantity, // quantity
                BigDecimal.valueOf(1.1), // conversionMultiple
                quantity.multiply(BigDecimal.valueOf(1.1))// totalCalculatedAmount
        );

        return currencyConversion;
    }


    @RabbitListener(queues = "currencyExchangeQueue")
    public void receiveCurrencyExchangeFromRabbit(@Payload CurrencyConversion currencyConversion) {
        try {
            // Kuyruğun var olup olmadığını kontrol et
            if (!isQueueExist("currencyExchangeQueue")) {
                log.warn("currencyExchangeQueue kuyruğu mevcut değil, mesaj alımı yapılamadı.");
                return; // Kuyruk yoksa işlemi sonlandır
            }

            // Kuyruk varsa, gelen mesajı işleyin
            System.out.println("Received from RabbitMQ: " + currencyConversion);

        } catch (Exception e) {
            log.error("RabbitMQ kuyruğu ile ilgili bir hata oluştu, ancak uygulama devam ediyor: ", e);
        }
    }

    // Kuyruğun var olup olmadığını kontrol etmek için yardımcı bir yöntem
    private boolean isQueueExist(String queueName) {
        try {
            // Kuyruğu kontrol et (passive mode ile)
            amqpAdmin.declareQueue(new Queue(queueName, false, false, true));
            return true;
        } catch (Exception e) {
            log.warn("Kuyruk kontrol edilirken bir hata oluştu: " + e.getMessage());
            return false; // Kuyruk yoksa false döner
        }
    }



}
