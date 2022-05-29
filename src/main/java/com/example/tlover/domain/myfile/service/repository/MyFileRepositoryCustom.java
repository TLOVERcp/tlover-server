package com.example.tlover.domain.myfile.service.repository;

import com.example.tlover.domain.myfile.entity.MyFile;

import java.util.Optional;

public interface MyFileRepositoryCustom {
    Optional<MyFile> findOneNotDeletedByFileId(Long fileId);
}
