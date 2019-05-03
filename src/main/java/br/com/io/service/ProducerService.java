package br.com.io.service;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import br.com.io.entity.Order;

@Service
public class ProducerService {

	@Autowired
	private ConnectionFactory rabbitConnectionFactory;
	
	@Autowired
	private Set<String> consumersId;

	public void produceOrder(Order order) {

		String exchangeName = "io_fanout";
		
		try (Connection connection = rabbitConnectionFactory.newConnection(); 
			Channel channel = connection.createChannel()) {
			channel.exchangeDeclare(exchangeName, BuiltinExchangeType.FANOUT, true);
			
			consumersId.forEach(consumerId -> {
				try {
					channel.queueDeclare(consumerId, true, false, false, null);
					channel.queueBind(consumerId, exchangeName, "");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			
			Gson gson = new Gson();
			String orderJson = gson.toJson(order);
			channel.basicPublish(exchangeName, "", null, orderJson.getBytes());
			
			System.out.println("PRODUCING order" + orderJson);
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
	}
}
