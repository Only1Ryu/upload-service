package com.app.eventrade.uploadservice.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.app.eventrade.uploadservice.dto.Response;
import com.app.eventrade.uploadservice.dto.ResponseFile;
import com.app.eventrade.uploadservice.entity.UploadEntity;
import com.app.eventrade.uploadservice.exception.CustomException;
import com.app.eventrade.uploadservice.exception.NotFoundException;
import com.app.eventrade.uploadservice.service.UploadService;
import com.app.eventrade.uploadservice.util.AppConstants;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(AppConstants.UPLOAD)
@RefreshScope
@Api(tags = "Upload Service API")
public class UploadController {

	@Autowired
	private UploadService uploadService;

	private static LocalDateTime timeStamp = LocalDateTime.now();

	@PostMapping(value = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> getByKycId(@RequestParam UUID id, @RequestPart("file") MultipartFile file,
			@RequestParam("docName") String docName) throws IOException, CustomException {
		if (id == null || file.isEmpty()) {
			throw new CustomException(AppConstants.NO_UUID + " or " + AppConstants.NO_FILE);
		}
		Response response = new Response();
		response.setResponse(uploadService.upload(id, file, docName));
		response.setStatusCode(HttpStatus.OK.value());
		response.setTimeStamp(timeStamp);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/file/{id}")
	public ResponseEntity<List<ResponseFile>> getListFiles(@PathVariable UUID id) {
		List<ResponseFile> files = uploadService.getAllFilesByKycId(id).stream().map(dbFile -> {
			String fileDownloadUri = ServletUriComponentsBuilder
					.fromCurrentContextPath().path(AppConstants.UPLOAD+"/file/")
					.path(String.valueOf(dbFile.getKycId()))
					.path("/"+dbFile.getDocName())
					.toUriString();
			return new ResponseFile(dbFile.getFileName(), fileDownloadUri, dbFile.getFileType(), dbFile.getDocName(),
					dbFile.getFileData().length);
		}).toList();
		return ResponseEntity.status(HttpStatus.OK).body(files);
	}

	@GetMapping("/file/{id}/{docName}")
	public ResponseEntity<byte[]> getFileByUserIdAndDocName(@PathVariable UUID id,@PathVariable String docName) throws NotFoundException {
		UploadEntity uploadEntity = uploadService.getFileByKycIdAndDocName(id, docName);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + uploadEntity.getFileName() + "\"")
				.body(uploadEntity.getFileData());
	}
	
	@DeleteMapping("/file/{id}")
	public ResponseEntity<Object> deleteFileById(@PathVariable UUID id) throws NotFoundException {
		if(id == null)
		{
			throw new NotFoundException(AppConstants.REQUEST_BODY_MISSING);
		}
		Response response = new Response();
		response.setResponse(uploadService.deleteFileById(id));
		response.setStatusCode(HttpStatus.OK.value());
		response.setTimeStamp(timeStamp);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
}
