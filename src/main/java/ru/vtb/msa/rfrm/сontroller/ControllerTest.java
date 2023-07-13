package ru.vtb.msa.rfrm.сontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.vtb.msa.rfrm.kafka.model.ObjectRewardReq;
import ru.vtb.msa.rfrm.service.ServiceTest;

@RestController
@RequiredArgsConstructor
public class ControllerTest {

    private final ServiceTest serviceTest;

    @Autowired
    KafkaTemplate<String, ObjectRewardReq> kafkaTemplate;
    private static final String TOPIC = "RewardReq";

    @GetMapping("/hello")
    public String hello() {

        serviceTest.test();
        return "Hello!";
    }

    /*** Тест отправки объекта в топик кафка */
    @PostMapping("/publish")
    public String publishMessage(@RequestBody ObjectRewardReq rewardReq) {
        kafkaTemplate.send(TOPIC, rewardReq);
        return "Object published in topic successfully!";
    }

}
