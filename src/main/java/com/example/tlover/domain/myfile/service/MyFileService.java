package com.example.tlover.domain.myfile.service;

import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.myfile.entity.MyFile;
import com.example.tlover.domain.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MyFileService {
    List<MyFile> saveImages(List<MultipartFile> multipartFiles);
    MyFile saveImage(MultipartFile multipartFiles);
    MyFile getFile(Long fileId);
    boolean deleteFile(Long fileId);
    List<MyFile> findByUserAndDiary(User user, Diary diary);
}
