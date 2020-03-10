package com.technokryon.ecommerce.admin.pojo;

import java.time.OffsetDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserGroup {

	String ugId;
	String ugName;
	String ugDescription;
	String ugStatusYN;
	OffsetDateTime ugCreatedDate;
	String ugCreatedUserId;
	OffsetDateTime ugModifiedDate;
	String ugModifiedUserId;

}
