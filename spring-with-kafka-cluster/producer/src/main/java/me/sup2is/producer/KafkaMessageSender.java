package me.sup2is.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class KafkaMessageSender {

    @Autowired
    private KafkaTemplate<String, GCMPushEntity> kafkaTemplate;

    @Value("${kafka.my.push.topic.name}")
    private String topicName;

    public void send(GCMPushEntity gcmPushEntity) {

        Message<GCMPushEntity> message = MessageBuilder
                .withPayload(gcmPushEntity)
                .setHeader(KafkaHeaders.TOPIC, topicName)
                .build();

        ListenableFuture<SendResult<String, GCMPushEntity>> future =
                kafkaTemplate.send(message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, GCMPushEntity>>() {

            @Override
            public void onSuccess(SendResult<String, GCMPushEntity> stringObjectSendResult) {
                System.out.println("Sent message=[" + stringObjectSendResult.getProducerRecord().value().toString() +
                        "] with offset=[" + stringObjectSendResult.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=[] due to : " + ex.getMessage());
            }
        });
    }

}
