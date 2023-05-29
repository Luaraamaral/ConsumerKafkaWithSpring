package br.com.luara.consumidorkafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class ConsumidorkafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumidorkafkaApplication.class, args);
	}

}
