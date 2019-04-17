package com.zcx.rabbitmq;

import com.zcx.rabbitmq.pojo.Order;
import com.zcx.rabbitmq.producer.OrderSender;
import com.zcx.rabbitmq.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqProducerApplicationTests {

	@Test
	public void contextLoads() {
	}

	// -------- 测试 OrderSend1 --------
	@Autowired
	private OrderSender orderSender;

	@Test
	public void testSend1() throws Exception {
		Order order = new Order();
		order.setId("20190416000000001");
		order.setName("rabbitmq-测试发送订单消息1");
		order.setMessageId(System.currentTimeMillis() + "$" + UUID.randomUUID());
		orderSender.sendOrder(order);
	}

	// -------- 测试 OrderSend2 --------
	@Autowired
	private OrderService orderService;

	@Test
	public void testSend2() throws Exception {
		Order order = new Order();
		order.setId(UUID.randomUUID().toString());
		order.setName("rabbitmq-测试发送订单消息2");
		order.setMessageId(System.currentTimeMillis() + "$" + UUID.randomUUID());
		try {
			orderService.createOrder(order);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
