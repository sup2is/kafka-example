package me.sup2is.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GCMPushRestController {

    @Autowired
    private KafkaMessageSender kafkaMessageSender;

    @PostMapping("/push")
    public String push(@RequestBody GCMPushEntity gcmPushEntity) {
        kafkaMessageSender.send(gcmPushEntity);
        return "success";
    }

}
