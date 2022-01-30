package com.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PublisherConfig {

	@Value("${app.rabbitmq.exchange}")
	private String exchangeName;

	@Value("${app.rabbitmq.queue}")
	private String queueName;

	@Value("${app.rabbitmq.routingkey}")
	private String routingKeyName;
	
	@Value("${app.rabbitmq.dlExchange}")
	private String deadLetterExchangeName;

	@Value("${app.rabbitmq.dlQueue}")
	private String deadLetterQueueName;

	@Value("${app.rabbitmq.dlRoutingkey}")
	private String deadLetterRoutingKeyName;

	/*@Bean
	public Declarables configure() {
		Map<String, Object> args = new HashMap<>();
		args.put("x-dead-letter-exchange", deadLetterExchangeName);
		args.put("x-dead-letter-routing-key", deadLetterRoutingkeyName);

		Queue queueForDlqDemo = new Queue(queueName, false, false, false, args);

		TopicExchange exchange = new TopicExchange(exchangeName, false, false);
		Queue deadLetterQueue = new Queue(deadLetterQueueName, false);
		TopicExchange deadLetterExchange = new TopicExchange(deadLetterExchangeName, false, false);
		return new Declarables(exchange, queueForDlqDemo,
				BindingBuilder.bind(queueForDlqDemo).to(exchange).with(routingkeyName), deadLetterExchange,
				deadLetterQueue,
				BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with(deadLetterRoutingkeyName));
	}*/
	
	@Bean
	TopicExchange deadLetterExchange() {
		return new TopicExchange(deadLetterExchangeName);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(exchangeName);
	}

	@Bean
	Queue deadLetterQueue() {
		return QueueBuilder.durable(deadLetterQueueName).build();
	}

	@Bean
	Queue queue() {
		return QueueBuilder.durable(queueName).withArgument("x-dead-letter-exchange", deadLetterExchangeName)
				.withArgument("x-dead-letter-routing-key", deadLetterRoutingKeyName).build();
	}

	@Bean
	Binding deadLetterBinding() {
		return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(deadLetterRoutingKeyName);
	}

	@Bean
	Binding binding() {
		return BindingBuilder.bind(queue()).to(exchange()).with(routingKeyName);
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public MessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public AmqpTemplate template(ConnectionFactory connectionFactory) {
		final RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setMessageConverter(messageConverter());
		return template;
	}
}
