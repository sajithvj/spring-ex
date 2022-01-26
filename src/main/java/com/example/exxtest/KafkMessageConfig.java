package com.example.exxtest;

import com.example.exxtest.serializer.KafkaMessageSerilizer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableAutoConfiguration
public class KafkMessageConfig {
    @Value("${kafka.bootstrap.servers}")
    private String kafkaBootstrapServers;

    public Map producerConfigs() {
        Map props = new HashMap();
        // list of host:port pairs used for establishing the initial connections
        // to the Kafka cluster
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,kafkaBootstrapServers);
        //message key serialization format when sending
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaMessageSerilizer.class);
        // value to block, after which it will throw a TimeoutException
        props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 5000);
        //props.put("compression.codec",compressionCodec);
       props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG,"snappy");
        return props;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean(name="producerFactory")
    public ProducerFactory producerFactory() {
        return new DefaultKafkaProducerFactory(producerConfigs());
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean(name="kafkaTemplate")
    public KafkaTemplate kafkaTemplate() {
        return new KafkaTemplate(producerFactory());
    }


}
