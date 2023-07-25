package ru.vtb.msa.rfrm.сontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.vtb.msa.rfrm.integration.rfrmcore.model.ObjectRewardReq;
import ru.vtb.msa.rfrm.integration.rfrmcore.model.RewardSerializer;
import ru.vtb.msa.rfrm.service.ServiceAccounts;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ControllerTest {

    private final ServiceAccounts serviceAccounts;

    private final KafkaTemplate<Object, byte[]> kafkaTemplate;
    private static final String TOPIC = "RewardReq";

    @GetMapping("/getaccounts")
    public String hello() throws JSONException {

        serviceAccounts.getClientAccounts();
        return "Accounts for clients are received !";
    }

    private final RewardSerializer rewardSerializer;

    /*** Тест получения объекта из топика RewardReq кафка */
    @PostMapping("/publish")
    public String publishMessage(@RequestBody ObjectRewardReq rewardReq) throws IOException {


        ObjectMapper objectMapper = new ObjectMapper();
        //ObjectRewardReq readValue = objectMapper.readValue(rewardReq, ObjectRewardReq.class);

        byte[] serialize = rewardSerializer.serialize(TOPIC, rewardReq);

        kafkaTemplate.send(TOPIC, serialize);
        return "Object published in topic successfully!";
    }

    /** Тест сохранения в БД нового задания через JDBC */
    @GetMapping("/savetask")
    public void saveNewTask() {
        serviceAccounts.saveNewTaskToPayPaymentTask();
    }

}
