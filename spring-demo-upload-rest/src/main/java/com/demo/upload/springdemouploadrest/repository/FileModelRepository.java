package com.demo.upload.springdemouploadrest.repository;

import com.demo.upload.springdemouploadrest.entity.FileModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileModelRepository extends JpaRepository<FileModel, Long> {
}
