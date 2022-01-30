package com.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.Book;

@RestController
@RequestMapping(value = "/app")
public class PublishMessage {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Value("${app.rabbitmq.exchange}")
	private String exchange;

	@Value("${app.rabbitmq.routingkey}")
	private String routingkey;


	@PostMapping(value = "/publish")
	public String publishMessage(@RequestBody Book book) {
		rabbitTemplate.convertAndSend(exchange, routingkey, book);
		return "Book published: " + book;
	}

}
