package com.example.tlover.domain.myfile.service;

import com.example.tlover.domain.myfile.entity.MyFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MyFileService {
    List<MyFile> saveImages(List<MultipartFile> multipartFiles);
    MyFile saveImage(MultipartFile multipartFiles);
    MyFile getFile(Long fileId);
    boolean deleteFile(Long fileId);
}
