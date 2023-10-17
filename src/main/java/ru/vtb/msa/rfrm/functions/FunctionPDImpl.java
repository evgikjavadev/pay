package ru.vtb.msa.rfrm.functions;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.vtb.msa.rfrm.integration.kafkainternal.KafkaInternalProducer;
import ru.vtb.msa.rfrm.integration.kafkainternal.model.InternalMessageModel;
import ru.vtb.msa.rfrm.integration.util.enums.Statuses;
import ru.vtb.msa.rfrm.processingDatabase.batch.ActionEntPaymentTaskRepo;
import ru.vtb.msa.rfrm.processingDatabase.model.DctTaskStatuses;
import ru.vtb.msa.rfrm.processingDatabase.model.EntPaymentTask;
import ru.vtb.msa.rfrm.repository.EntPaymentTaskRepository;
import ru.vtb.msa.rfrm.service.ServiceAccounts;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/** Функция ПД. Подготовка данных для выплаты вознаграждения участнику РФП */
@Slf4j
@Service
@RequiredArgsConstructor
public class FunctionPDImpl implements FunctionPD {
    @Value("${findSizeApplication}")
    private Integer findSizeApplication;
    @Value("function.kafka.topic.rfrm_pay_function_result_reward")
    private String rfrm_pay_function_result_reward;
    private final EntPaymentTaskRepository entPaymentTaskRepository;
    private final ActionEntPaymentTaskRepo actionEntPaymentTaskRepo;
    private final ServiceAccounts serviceAccounts;
    private final KafkaInternalProducer kafkaInternalProducer;
    @Override
    public void startFunctionPD() {
        log.info("Start function PD. ПД. Подготовка данных для выплаты вознаграждения участнику РФП");

        // Осуществить поиск N заданий в таблице paymentTask, у которых status=10 (Новая) и blocked=0, с сортировкой по blocked_at возрастанию
        List<EntPaymentTask> entPaymentTaskList = entPaymentTaskRepository.findByStatus(DctTaskStatuses.STATUS_NEW.getStatus(), findSizeApplication);

        // передаем в обработку List задач
        handleMdmIdList(entPaymentTaskList);

        // отправить сообщение в rfrm_pay_function_result_reward для инициации следующего цикла обработки заданий
        kafkaInternalProducer.sendObjectToInternalKafka(rfrm_pay_function_result_reward, createMessageToTopicInternal());

        log.info("Finish function PD. ПД. Подготовка данных для выплаты вознаграждения участнику РФП");
    }

    @SneakyThrows
    private void handleMdmIdList(List<EntPaymentTask> entPaymentTaskList) {

        List<Integer> setRewardIdList = entPaymentTaskList.stream().map(EntPaymentTask::getRewardId).distinct().collect(Collectors.toList());

        //Установить для задачи blocked=1 и blocked_at=now()
        actionEntPaymentTaskRepo.updateBlockByRewardIdEqualOne(setRewardIdList);

        for (EntPaymentTask elem: entPaymentTaskList) {
            // получаем счета клиента из 1503 для каждого mdmId и rewardId
            serviceAccounts.getClientAccounts(elem.getMdmId(), elem.getRewardId());
        }

        // Установить для задачи blocked=0 и blocked_at=now()
        actionEntPaymentTaskRepo.updateBlockByRewardIdEqualZero(setRewardIdList);

        //Thread.sleep(sleepMs);
    }

    private InternalMessageModel createMessageToTopicInternal() {

        return InternalMessageModel
                .builder()
                .functionName("function_result_reward")
                .status(Statuses.COMPLETED.name())
                .timeStamp(LocalDateTime.now())
                .build();
    }
}
