package ru.vtb.msa.rfrm.integration.personaccounts.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.web.reactive.function.client.WebClient;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.request.AccountInfoRequest;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.response.CommonResponseAccounts;
import ru.vtb.msa.rfrm.integration.personaccounts.client.model.response.Response;
import ru.vtb.msa.rfrm.integration.personaccounts.config.ProductProfileFL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PersonClientAccountsImplTest {

    @Mock
    private PersonClientAccounts personClientAccounts;
    @Mock
    private AccountInfoRequest request;

    Response<CommonResponseAccounts<?>> response = new Response<>();
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetPersonAccounts() {
        Long mdmId = 12345L;

        when(personClientAccounts.getPersonAccounts(eq(mdmId), any()))
                .thenReturn(new Response<>());

        Response<?> result = personClientAccounts.getPersonAccounts(mdmId, request);

        assertEquals(response, result);
    }
}