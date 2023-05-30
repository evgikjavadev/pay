package ru.vtb.msa.rfrm.integration.personcs.client.model.person.request;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class MessageRequest{

	@ArraySchema(maxItems = 10, minItems = 1, schema = @Schema(implementation = PersonItem.class, description = "Информация о ФЛ"))
	private List<PersonItem> person;
}