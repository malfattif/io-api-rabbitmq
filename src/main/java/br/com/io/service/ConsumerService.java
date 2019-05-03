package br.com.io.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import br.com.io.entity.Order;

@Service
public class ConsumerService {
	
	@Autowired
	private ConnectionFactory rabbitConnectionFactory;
	
	@Autowired
	private Set<String> consumersId;
	
	private HashMap<String, List<Order>> ordersByConsumerId = new HashMap<>();

	public String subscribe() {
		try {
			Connection connection = rabbitConnectionFactory.newConnection();
			Channel channel = connection.createChannel();
			String  consumeId = getConsumeId();

			consumersId.add(consumeId);
			channel.queueDeclare(consumeId, true, false, false, null);
			 
		    DeliverCallback deliverCallback = (consumerTag, delivery) -> {
		        String message = new String(delivery.getBody(), "UTF-8");
		        Order order = new Gson().fromJson(message, Order.class);
		        ordersByConsumerId.putIfAbsent(consumeId,  new ArrayList<Order>() );
		        ordersByConsumerId.computeIfPresent(consumeId, (k, v) -> { v.add(order); return v;});
		        System.out.println(" Consuming order '" + message + "' ConsumeId - " + consumeId);
		        delivery.getEnvelope();
		        
		    };
		    channel.basicConsume(consumeId, true, deliverCallback, consumerTag -> { });
		    
		    
		    return consumeId;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	private String getConsumeId() throws NoSuchAlgorithmException {
		String randomConsumeId = String.valueOf(Math.random() * 101);
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(randomConsumeId.getBytes());
		BigInteger no = new BigInteger(1, md.digest());
		String hashtext = no.toString(16);
		while (hashtext.length() < 32) {
			hashtext = "0" + hashtext;
		}
		return hashtext;
	}


	public List<Order> getOrders(String consumeId) {
		return ordersByConsumerId.get(consumeId);
	}
}
