package com.app.eventrade.uploadservice.repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.eventrade.uploadservice.entity.UploadEntity;

@Repository
@Transactional
public interface UploadRepository extends JpaRepository<UploadEntity, UUID> {

	Optional<UploadEntity> findByKycIdAndDocName(UUID id,String docName);
	
	Collection<UploadEntity> findByKycId(UUID id);
}
