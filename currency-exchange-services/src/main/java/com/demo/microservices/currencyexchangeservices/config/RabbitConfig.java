package com.demo.microservices.currencyexchangeservices.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class RabbitConfig {

    private final RabbitTemplate rabbitTemplate;

    // RabbitTemplate'i constructor ile enjekte edelim
    public RabbitConfig(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Bean
    public Queue currencyExchangeQueue() {
        return new Queue("currencyExchangeQueue", true);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("currencyExchangeExchange");
    }

    @Bean
    public Binding binding(Queue currencyExchangeQueue, DirectExchange exchange) {
        return BindingBuilder.bind(currencyExchangeQueue).to(exchange).with("currencyExchangeRoutingKey");
    }

    // @PostConstruct ile kuyruğun oluşturulmasını sağlayalım
    @PostConstruct
    public void createQueue() {
        // RabbitTemplate ile kuyruk oluşturma işlemi
        rabbitTemplate.execute(channel -> {
            channel.queueDeclare("currencyExchangeQueue", true, false, false, null); // Kuyruk oluşturma
            return null;
        });

        System.out.println("Currency Exchange Queue created.");
    }
}
