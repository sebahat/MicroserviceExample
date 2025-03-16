package com.demo.microservices.currencyconversionservices;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnableRabbit
class CurrencyConversionServicesApplicationTests {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Test
	void contextLoads() {
	}
	/*@Test
	void testCurrencyConversionQueue() {
		// Kuyruğa bir mesaj gönder
		rabbitTemplate.convertAndSend("currencyExchangeQueue", "Test message");

		// Burada, kuyruğun doğru şekilde çalıştığını ve mesajın gönderildiğini doğrulamak için ek testler yapabilirsiniz.
		// Örneğin, RabbitListener'dan gelen mesajları kontrol edebilirsiniz.
		// Test mantığını burada uygulayabilirsiniz.
	}*/
}
