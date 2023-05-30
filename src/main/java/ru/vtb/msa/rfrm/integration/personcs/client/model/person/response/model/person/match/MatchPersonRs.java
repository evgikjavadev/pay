package ru.vtb.msa.rfrm.integration.personcs.client.model.person.response.model.person.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Корневой элемент клиентских данных.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchPersonRs {

    private MatchPersonData messageResponse;
}
