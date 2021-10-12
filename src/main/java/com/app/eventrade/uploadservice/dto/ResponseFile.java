package com.app.eventrade.uploadservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseFile {
	
	@JsonProperty("fileName")
	private String fileName;
	
	@JsonProperty("uri")
	private String uri;
	
	@JsonProperty("fileType")
	private String fileType;
	
	@JsonProperty("docName")
	private String docName;
	
	@JsonProperty("size")
	private long size;
}
