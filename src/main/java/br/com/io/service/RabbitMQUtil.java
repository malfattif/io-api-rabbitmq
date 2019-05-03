package br.com.io.service;

import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.ConnectionFactory;

@Component
public class RabbitMQUtil {
	
	@Value("${rabbit.host}")
	private String rabbitHost;

	@Value("${rabbit.port:5672}")
	private Integer rabbitPort;

	@Value("${rabbit.username}")
	private String username;

	@Value("${rabbit.password}")
	private String password;

	@Bean
	public ConnectionFactory getRabbitConnectionFactory() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(rabbitHost);
		factory.setPort(rabbitPort);
		factory.setUsername(username);
		factory.setPassword(password);
		return factory;
	}
	
	@Bean
	public Set<String> getConsumersId(){
		return new TreeSet<String>();
	}
}
