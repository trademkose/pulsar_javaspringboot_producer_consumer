package io.github.trademkose.pulsar.bus;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.github.trademkose.pulsar.annotation.PulsarConsumer;
import io.github.trademkose.pulsar.bus.msg.MyMsg;
import io.github.trademkose.pulsar.constant.Serialization;

@Service
public class TestConsumers {

	public AtomicBoolean mockTopicListenerReceived = new AtomicBoolean(false);
	public AtomicBoolean stringTopicReceived = new AtomicBoolean(false); 
	private static final Logger LOGGER = LoggerFactory.getLogger(TestConsumers.class);

	@PulsarConsumer(topic = "topic-string", clazz = String.class, serialization = Serialization.STRING)
	public void byteTopic(String stringMsg) {

		LOGGER.info("@PulsarConsumer Consumed data : " + stringMsg);
		stringTopicReceived.set(true);
	}

	@PulsarConsumer(topic = "topic-one", clazz = MyMsg.class, serialization = Serialization.JSON)
	public void topicOneListener(MyMsg myMsg) {
		LOGGER.info("@PulsarConsumer Consumed data : " + myMsg.getData());
		mockTopicListenerReceived.set(true);
	}
}
