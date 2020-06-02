package me.sup2is.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.Scanner;

public class MyProducer {

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG
                , "192.168.56.107:9092,192.168.56.107:9093,192.168.56.107:9094");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        Producer<String, String> producer = new KafkaProducer<>(properties);

        while(true) {
            Scanner sc = new Scanner(System.in);
            System.out.print("Input > ");
            String message = sc.nextLine();

            ProducerRecord<String, String> record = new ProducerRecord<>("my-test-topic", message);
            try {
                producer.send(record, (metadata, exception) -> {
                    if (exception != null) {
                        // some exception
                        exception.printStackTrace();
                    }
                });

            } catch (Exception e) {
                // exception
            } finally {
                producer.flush();
            }

            if(message.equals("quit")) {
                producer.close();
                break;
            }
        }
    }
}
