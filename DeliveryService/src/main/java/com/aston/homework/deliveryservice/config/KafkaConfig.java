package com.aston.homework.deliveryservice.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@RequiredArgsConstructor
public class KafkaConfig {

    private final Environment environment;

    @Bean
    ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getProperty("spring.kafka.bootstrap-servers"));
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(ProducerConfig.ACKS_CONFIG, environment.getProperty("spring.kafka.producer.acks"));
        config.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, environment.getProperty("spring.kafka.producer.properties.delivery.timeout.ms"));
        config.put(ProducerConfig.LINGER_MS_CONFIG, environment.getProperty("spring.kafka.producer.properties.linger.ms"));
        config.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, environment.getProperty("spring.kafka.producer.properties.request.timeout.ms"));
        config.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, environment.getProperty("spring.kafka.producer.properties.enable.idempotence"));
        config.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, environment.getProperty("spring.kafka.producer.properties.max.in.flight.requests.per.connection"));
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, Object> orderKafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public KafkaTemplate<String, Object> paymentKafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public KafkaTemplate<String, Object> deliveryKafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put("bootstrap.servers", "localhost:9092");
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic paymentRequestTopic() {
        return TopicBuilder.name(environment.getProperty("kafka-topic-name.payment-request-topic"))
                .partitions(Integer.parseInt(environment.getProperty("topic.partitions-count")))
                .replicas(Integer.parseInt(environment.getProperty("topic.replicas-count")))
                .configs(Map.of("min.insync.replicas", environment.getProperty("topic.min-insync-replicas")))
                .build();
    }

    @Bean
    public NewTopic paymentResponseTopic() {
        return TopicBuilder.name(environment.getProperty("kafka-topic-name.payment-response-topic"))
                .partitions(Integer.parseInt(environment.getProperty("topic.partitions-count")))
                .replicas(Integer.parseInt(environment.getProperty("topic.replicas-count")))
                .configs(Map.of("min.insync.replicas", environment.getProperty("topic.min-insync-replicas")))
                .build();
    }

    @Bean
    public NewTopic deliveryRequestTopic() {
        return TopicBuilder.name(environment.getProperty("kafka-topic-name.delivery-request-topic"))
                .partitions(Integer.parseInt(environment.getProperty("topic.partitions-count")))
                .replicas(Integer.parseInt(environment.getProperty("topic.replicas-count")))
                .configs(Map.of("min.insync.replicas", environment.getProperty("topic.min-insync-replicas")))
                .build();
    }

    @Bean
    public NewTopic deliveryResponseTopic() {
        return TopicBuilder.name(environment.getProperty("kafka-topic-name.delivery-response-topic"))
                .partitions(Integer.parseInt(environment.getProperty("topic.partitions-count")))
                .replicas(Integer.parseInt(environment.getProperty("topic.replicas-count")))
                .configs(Map.of("min.insync.replicas", environment.getProperty("topic.min-insync-replicas")))
                .build();
    }

    @Bean
    public NewTopic orderCompensationTopic() {
        return TopicBuilder.name(environment.getProperty("kafka-topic-name.order-compensation-topic"))
                .partitions(Integer.parseInt(environment.getProperty("topic.partitions-count")))
                .replicas(Integer.parseInt(environment.getProperty("topic.replicas-count")))
                .configs(Map.of("min.insync.replicas", environment.getProperty("topic.min-insync-replicas")))
                .build();
    }

}