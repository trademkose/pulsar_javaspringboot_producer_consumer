package io.github.trademkose.pulsar.producer;

import org.apache.commons.lang3.tuple.ImmutablePair;

import io.github.trademkose.pulsar.constant.Serialization;

import java.util.Map;

public interface PulsarProducerFactory {
    Map<String, ImmutablePair<Class<?>, Serialization>> getTopics();
}
