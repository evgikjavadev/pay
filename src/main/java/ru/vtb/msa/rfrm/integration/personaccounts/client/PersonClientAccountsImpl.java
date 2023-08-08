package ru.vtb.msa.rfrm.integration.personaccounts.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.request.AccountInfoRequest;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.response.Account;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.response.CommonResponseAccounts;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.response.Response;
import ru.vtb.msa.rfrm.integration.personaccounts.config.ProductProfileFL;
import ru.vtb.msa.rfrm.integration.util.client.WebClientBase;


import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.vtb.msa.rfrm.integration.util.enums.ClientName.PRODUCT_PROFILE_FL;

@Slf4j
public class PersonClientAccountsImpl extends WebClientBase implements PersonClientAccounts {
    private final ProductProfileFL properties;

    public PersonClientAccountsImpl(WebClient webClient, ProductProfileFL properties) {
        super(properties.getRetry().getCount(), properties.getRetry().getDuration(), properties.getHeaders(), webClient);
        this.properties = properties;
    }

    @Override
    public Response<?> getPersonAccounts(AccountInfoRequest request) {
        log.info("Старт вызова {}", PRODUCT_PROFILE_FL.getValue());

        Response<?> post = this.post(uriBuilder -> uriBuilder.path(properties.getResource()).build(),
                request, CommonResponseAccounts.class);

        // проверяем есть ли номер счета в ответе
        System.out.println("my77 == " +
                post.getBody().getAccounts()
        );


        log.info("Финиш вызова {}", PRODUCT_PROFILE_FL.getValue());
        return post;
    }

}
