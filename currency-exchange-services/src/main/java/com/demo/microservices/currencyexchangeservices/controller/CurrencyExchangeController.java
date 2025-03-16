package com.demo.microservices.currencyexchangeservices.controller;


import com.demo.microservices.currencyexchangeservices.dto.CurrencyExchange;
import com.demo.microservices.currencyexchangeservices.repository.CurrencyExchangeRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {


    Logger logger =LoggerFactory.getLogger(CurrencyExchangeController.class);

   /* @Autowired
    private Environment environment;
*/
    @Autowired
    private RabbitTemplate rabbitTemplate;



    @Autowired
    private CurrencyExchangeRepository currencyExchangeRepository;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange  retriveExchangeValue(@PathVariable String from , @PathVariable String to){
         //CurrencyExchange currencyExchange = new CurrencyExchange(100L,from,to,BigDecimal.valueOf(50));
         CurrencyExchange currencyExchange = currencyExchangeRepository.findByFromAndTo(from,to);
         logger.error("Get test currentrecive");
         if(currencyExchange == null){
             throw  new RuntimeException("Unable to find data for " + from + " to " + to);
         }
  //       String port = environment.getProperty("local.server.port");
         return  currencyExchange;
    }

      //rabitmq enpoint

    @GetMapping("/currency-exchange-rabbit/from/{from}/to/{to}")
    public CurrencyExchange retriveExchangeValuefromRabbit(@PathVariable String from, @PathVariable String to) {
        CurrencyExchange currencyExchange = currencyExchangeRepository.findByFromAndTo(from, to);
        logger.error("Test currency exchange recevied data");
        if (currencyExchange == null) {
            throw new RuntimeException("Unable to find data for " + from + " to " + to);
        }

        // RabbitMQ'ya döviz değişim bilgisini gönder
        sendCurrencyExchangeToRabbitMQ(currencyExchange);

       // String port = environment.getProperty("local.server.port");
        return currencyExchange;
    }

    private void sendCurrencyExchangeToRabbitMQ(CurrencyExchange currencyExchange) {
        // RabbitMQ'ya gönderilen mesaj
        rabbitTemplate.convertAndSend("currencyExchangeQueue", currencyExchange);
    }

}
