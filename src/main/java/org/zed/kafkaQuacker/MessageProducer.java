package org.zed.kafkaQuacker;

import java.util.Properties;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class MessageProducer {

    private static MessageProducer instance;
    private Producer<String, byte[]> producer;

    private MessageProducer() {
    }

    public synchronized static MessageProducer getInstance() {
        if (instance == null) {
            instance = new MessageProducer();
        }
        return instance;
    }

    public void shutdownGracefully() {
        if (producer != null) {
            producer.close();
        }
    }

    public void send(String key, byte[] message) {
        ProducerRecord<String, byte[]> data = new ProducerRecord<>(ServiceConfig.QUACKER_TOPIC, key, message);
        TestCallback callback = new TestCallback();
        producer.send(data, callback);
    }

    private static class TestCallback implements Callback {
        @Override
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            if (e != null) {
                System.out.println("Error while producing message to topic :" + recordMetadata);
                e.printStackTrace();
            } else {
                String message = String.format("sent message to topic:%s partition:%s  offset:%s", recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset());
                System.out.println(message);
            }
        }
    }

    public void init() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, ServiceConfig.QUACKER_BOOTSTRAP_SERVER);

        //configure the following three settings for SSL Encryption
        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, ServiceConfig.QUACKER_SECURITY_PROTOCOL);
        if (ServiceConfig.QUACKER_SECURITY_PROTOCOL.equals("SSL")) {
            props.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, ServiceConfig.QUACKER_TRUSTSTORE);
            props.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, ServiceConfig.QUACKER_TRUSTSTORE_PASSWORD);
            props.put(SslConfigs.SSL_TRUSTSTORE_TYPE_CONFIG, "JKS");

            // configure the following three settings for SSL Authentication
            props.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, ServiceConfig.QUACKER_KEYSTORE);
            props.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, ServiceConfig.QUACKER_KEYSTORE_PASSWORD);
            props.put(SslConfigs.SSL_KEYSTORE_TYPE_CONFIG, "JKS");
            props.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, ServiceConfig.QUACKER_KEY_PASSWORD);
            props.put(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, "");
        }

        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        this.producer = new KafkaProducer<>(props);
    }
}
