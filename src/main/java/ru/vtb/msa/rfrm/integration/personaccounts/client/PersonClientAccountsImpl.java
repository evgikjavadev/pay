package ru.vtb.msa.rfrm.integration.personaccounts.client;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.request.AccountInfoRequest;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.response.CommonResponseAccounts;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.response.Response;
import ru.vtb.msa.rfrm.integration.personaccounts.config.ProductProfileFL;
import ru.vtb.msa.rfrm.integration.util.ErrorCounter;
import ru.vtb.msa.rfrm.integration.util.client.WebClientBase;

import javax.annotation.PostConstruct;

import static ru.vtb.msa.rfrm.integration.util.enums.ClientName.PRODUCT_PROFILE_FL;

@Slf4j
public class PersonClientAccountsImpl extends WebClientBase implements PersonClientAccounts {
    private final ProductProfileFL properties;
    private ErrorCounter errorCounter;
    private MeterRegistry registry;

    public PersonClientAccountsImpl(WebClient webClient, ProductProfileFL properties) {
        super(properties.getRetry().getCount(), properties.getRetry().getDuration(), webClient);
        this.properties = properties;
    }

    @PostConstruct
    private void init() {
        this.errorCounter = new ErrorCounter("rfrm_ppfl_integration_error_count_total",
                "Количество неудачных запросов к ИС 1503 Продуктовый профиль", "status",
                registry, getClass().getSimpleName(), "POST");
    }

    @Override
    public Response<?> getPersonAccounts(Long mdmId, AccountInfoRequest request) {
        log.info("Старт вызова {}", PRODUCT_PROFILE_FL.getValue());

        Response<?> post = this.post(
                mdmId,
                uriBuilder -> uriBuilder.path(properties.getResource()).build(),
                request,
                CommonResponseAccounts.class);

        log.info("Финиш вызова {}", PRODUCT_PROFILE_FL.getValue());
        return post;
    }

}
