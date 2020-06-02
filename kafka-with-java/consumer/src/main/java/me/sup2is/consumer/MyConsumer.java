package me.sup2is.consumer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class MyConsumer {
    public static void main( String[] args ) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "192.168.56.107:9092,192.168.56.107:9093,192.168.56.107:9094");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "my-test-topic");

        Consumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList("my-test-topic"));

        String message = null;
        try {
            do {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100000));

                for (ConsumerRecord<String, String> record : records) {
                    message = record.value();
                    System.out.println(message);
                }
            } while (!message.equals("quit"));
        } catch(Exception e) {
            e.printStackTrace();
            // exception
        } finally {
            consumer.close();
        }

    }

}
