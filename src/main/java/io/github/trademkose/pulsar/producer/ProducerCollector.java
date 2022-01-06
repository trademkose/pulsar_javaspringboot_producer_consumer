package io.github.trademkose.pulsar.producer;

import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

import io.github.trademkose.pulsar.annotation.PulsarProducer;
import io.github.trademkose.pulsar.collector.ProducerHolder;
import io.github.trademkose.pulsar.error.exception.ProducerInitException;
import io.github.trademkose.pulsar.utils.SchemaUtils;
import io.github.trademkose.pulsar.utils.UrlBuildService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class ProducerCollector implements BeanPostProcessor, EmbeddedValueResolverAware {

    private final PulsarClient pulsarClient;
    private final UrlBuildService urlBuildService;

    private final Map<String, Producer> producers = new ConcurrentHashMap<>();

    private StringValueResolver stringValueResolver;

    public ProducerCollector(PulsarClient pulsarClient, UrlBuildService urlBuildService) {
        this.pulsarClient = pulsarClient;
        this.urlBuildService = urlBuildService;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        final Class<?> beanClass = bean.getClass();

        if (beanClass.isAnnotationPresent(PulsarProducer.class) && bean instanceof PulsarProducerFactory) {
            producers.putAll(((PulsarProducerFactory) bean).getTopics().entrySet().stream()
                .map($ -> new ProducerHolder(
                    stringValueResolver.resolveStringValue($.getKey()),
                    $.getValue().left,
                    $.getValue().right))
                .collect(Collectors.toMap(ProducerHolder::getTopic, this::buildProducer)));
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }

    private Producer<?> buildProducer(ProducerHolder holder) {
        try {
            return pulsarClient.newProducer(getSchema(holder))
                .topic(urlBuildService.buildTopicUrl(holder.getTopic()))
                .create();
        } catch (PulsarClientException e) {
            throw new ProducerInitException("Failed to init producer.", e);
        }
    }

    private <T> Schema<?> getSchema(ProducerHolder holder) throws RuntimeException {
        return SchemaUtils.getSchema(holder.getSerialization(), holder.getClazz());
    }

    public Producer getProducer(String topic) {
        return producers.get(stringValueResolver.resolveStringValue(topic));
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver stringValueResolver) {
        this.stringValueResolver = stringValueResolver;
    }
}
