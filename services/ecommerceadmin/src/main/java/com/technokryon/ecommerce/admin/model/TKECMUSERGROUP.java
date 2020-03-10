package com.technokryon.ecommerce.admin.model;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TKECM_USER_GROUP")
@Data
@NoArgsConstructor

public class TKECMUSERGROUP {

	@Id
	@Column(name = "TKECMUG_ID")
	private String ugId;

	@Column(name = "TKECMUG_NAME")
	private String ugName;

	@Column(name = "TKECMUG_DESCRIPTION")
	private String ugDescription;

	@Column(name = "TKECMUG_STATUS_YN")
	private String ugStatusYN;

	@Column(name = "TKECMUG_CREATED_DATE")
	private OffsetDateTime ugCreatedDate;

	@Column(name = "TKECMUG_CREATED_USER_ID")
	private String ugCreatedUserId;

	@Column(name = "TKECMUG_MODIFIED_DATE")
	private OffsetDateTime ugModifiedDate;

	@Column(name = "TKECMUG_MODIFIED_USER_ID")
	private String ugModifiedUserId;

}
