package com.app.eventrade.uploadservice.service;

import java.io.IOException;
import java.util.Collection;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.app.eventrade.uploadservice.entity.UploadEntity;
import com.app.eventrade.uploadservice.exception.CustomException;
import com.app.eventrade.uploadservice.exception.NotFoundException;

public interface UploadService {
	public String upload(UUID id, MultipartFile file,String docName) throws IOException, CustomException;
	
	public Collection<UploadEntity> getAllFilesByKycId(UUID id);
	
	public UploadEntity getFileByKycIdAndDocName(UUID id,String docName) throws NotFoundException;
	
	public String deleteFileById(UUID id) throws NotFoundException;
}
