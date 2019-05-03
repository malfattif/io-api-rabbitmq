package br.com.io.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.io.entity.Order;
import br.com.io.service.ConsumerService;

@RestController
@RequestMapping(path = "v1/consumer", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
public class ConsumerController {
	
	@Autowired
	private ConsumerService consumerService;

	@GetMapping(path="/subscribe")
	public String subscribe() {
		return consumerService.subscribe();
	}
	
	@GetMapping(path="/consume-order/{consumeId}")
	public List<Order> consumeOrder(@PathVariable(name="consumeId") String consumeId) {
		return consumerService.getOrders(consumeId);
	}
}
