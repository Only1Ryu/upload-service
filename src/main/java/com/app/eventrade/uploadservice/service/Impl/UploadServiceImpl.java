package com.app.eventrade.uploadservice.service.Impl;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.app.eventrade.uploadservice.entity.UploadEntity;
import com.app.eventrade.uploadservice.exception.CustomException;
import com.app.eventrade.uploadservice.exception.NotFoundException;
import com.app.eventrade.uploadservice.repository.UploadRepository;
import com.app.eventrade.uploadservice.service.UploadService;
import com.app.eventrade.uploadservice.util.AppConstants;

@Service
public class UploadServiceImpl implements UploadService {

	@Autowired
	private UploadRepository uploadRepository;

	public String upload(UUID id, MultipartFile file, String docName) throws IOException, CustomException {
		if (!file.isEmpty()) {
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			UploadEntity uploadEntity = new UploadEntity();
			uploadEntity.setKycId(id);
			uploadEntity.setFileName(fileName);
			uploadEntity.setDocName(docName);
			uploadEntity.setCreationTime(new Date());
			uploadEntity.setFileType(file.getContentType());
			uploadEntity.setFileData(file.getBytes());
			uploadRepository.save(uploadEntity);
			return AppConstants.FILE_UPLOADED + " : " + fileName;
		}
		{
			throw new CustomException(AppConstants.FILE_SIZE);
		}
	}

	public Collection<UploadEntity> getAllFilesByKycId(UUID id) {
		return uploadRepository.findByKycId(id);
	}

	public UploadEntity getFileByKycIdAndDocName(UUID id, String docName) throws NotFoundException {
		if (id != null) {
			return uploadRepository.findByKycIdAndDocName(id, docName).get();
		}
		throw new NotFoundException(AppConstants.NO_FILE);
	}

	public String deleteFileById(UUID id) throws NotFoundException {
		if (id != null) {
			uploadRepository.deleteById(id);
			return AppConstants.FILE_DELETED;
		}
		throw new NotFoundException(AppConstants.NO_FILE);
	}
}
