package ru.vtb.msa.rfrm.integration.personaccounts.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.request.AccountInfoRequest;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.response.CommonResponseAccounts;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.response.Response;
import ru.vtb.msa.rfrm.integration.personaccounts.config.ProductProfileFL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class PersonClientAccountsImplTest {
    @Mock
    private WebClient webClient;
    @Mock
    private ProductProfileFL properties;

    private PersonClientAccountsImpl personClientAccounts;
    @Mock
    private AccountInfoRequest request;

    Response<CommonResponseAccounts<?>> response = new Response<>();
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        personClientAccounts = new PersonClientAccountsImpl(webClient, properties);
    }

    @Test
    void testGetPersonAccounts() {
        Long mdmId = 12345L;

        when(personClientAccounts.post(
                eq(mdmId),
                any(),
                eq(request),
                eq(CommonResponseAccounts.class)))
                .thenReturn(new Response<>());

        Response<?> result = personClientAccounts.getPersonAccounts(mdmId, request);

        verify(personClientAccounts).post(
                eq(mdmId),
                any(),
                eq(request),
                eq(CommonResponseAccounts.class));

        assertEquals(response, result);
    }
}