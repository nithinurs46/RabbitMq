package com.listeners;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.dto.Book;

@Service
@RabbitListener(queues = "${app.rabbitmq.queue}")
//@RabbitListener(bindings = @QueueBinding(value = @Queue(value = "${app.rabbitmq.queue}", autoDelete = "false") , exchange = @Exchange(value = "${app.rabbitmq.exchange}", autoDelete = "false") , key = "${app.rabbitmq.routingkey}") )
public class ApplicationListener {

	@RabbitHandler
	public void bookListener(@Payload Book book) throws AmqpRejectAndDontRequeueException {
		System.out.println("Consumed book:- " + book);
		if(book.getBookName().equals("Test-deadLetter")) {
			throw new AmqpRejectAndDontRequeueException("Exception occured, moving message to dead letter exchange, queue");
		}
	}
	
	
	@RabbitHandler
    public void receive(@Payload String message) {
        System.out.println("Message:- " + message);
    }
	
	/*@RabbitListener(queues = "${app.rabbitmq.dlQueue}")
	public void bookDLQueueListener(Book book) {
	    System.out.println("Book added to dead letter queue:- "+ book);
	}*/
}
