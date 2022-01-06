package io.github.trademkose.pulsar.bus;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.trademkose.pulsar.bus.msg.MyMsg;
import io.github.trademkose.pulsar.constant.Serialization;
import io.github.trademkose.pulsar.producer.ProducerFactory;

@Configuration
public class TestProducerConfiguration {

    @Bean
    public ProducerFactory producerFactory() {
        return new ProducerFactory()
        		.addProducer("topic-string", String.class, Serialization.STRING)
        		.addProducer("topic-one",MyMsg.class);
       
    }
}
