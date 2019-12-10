package com.technokryon.ecommerce.pojo;

import java.time.OffsetDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class PJ_TKECTOPTIONATTRIBUTE {

	String tkectoaId;
	String tkectoaAttributeId;
	String tkectoaName;
	OffsetDateTime tkectoaCreatedDate;
	String tkectoaCreatedUserId;
}
