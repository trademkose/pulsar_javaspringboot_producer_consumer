package io.github.trademkose.pulsar.bus;

import org.apache.pulsar.client.api.PulsarClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.trademkose.pulsar.bus.msg.MyMsg;
import io.github.trademkose.pulsar.producer.PulsarTemplate;

@Service
public class PulsarJavaSpringBootStarterApplicationTests { 

	@Autowired
	private PulsarTemplate<MyMsg> producer;

	@Autowired
	private PulsarTemplate<String> producerForStringTopic;

	private static final Logger LOGGER = LoggerFactory.getLogger(PulsarJavaSpringBootStarterApplicationTests.class);

	public void stringSerializationTestOk(String data) throws Exception {
		producerForStringTopic.send("topic-string", data);
		LOGGER.info("Producer sends this data to topic-string : " + data);
	}

	public void testProducerSendMethod(String data) throws PulsarClientException {
		producer.send("topic-one", new MyMsg(data));
		LOGGER.info("Producer sends this data to topic-one : " + new MyMsg(data).toString());

	}	
}
