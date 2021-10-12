package com.app.eventrade.uploadservice.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fileInfo")
@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
@DynamicUpdate
public class UploadEntity {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id")
	@JsonProperty("id")
	private UUID id;

	@JsonProperty("fileName")
	@Column(name = "fileName")
	private String fileName;
	
	@JsonProperty("fileType")
	@Column(name = "fileType")
	private String fileType;

	@JsonProperty("kycId")
	@Column(name = "kycId")
	private UUID kycId;

	@JsonProperty("docName")
	@Column(name = "docName")
	private String docName;

	@JsonProperty("fileData")
	@Lob
    @Column(name="fileData")
    private byte[] fileData;
	
	@JsonProperty("creationTime")
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "creationTime")
	private Date creationTime;

	@JsonProperty("updatedTime")
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "updatedTime")
	private Date updatedTime;
}
