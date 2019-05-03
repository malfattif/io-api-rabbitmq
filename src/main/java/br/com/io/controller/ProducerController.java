package br.com.io.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.io.entity.Order;
import br.com.io.service.ProducerService;


@RestController
@RequestMapping(path = "v1/producer")
public class ProducerController {
	
	@Autowired
	private ProducerService producerService;
	
	@PostMapping(path="/produce")
	public Boolean produceOrder(@RequestBody(required = true) Order order) {
		producerService.produceOrder(order);
		return true;
	}
}
