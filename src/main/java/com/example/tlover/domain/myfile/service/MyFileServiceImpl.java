package com.example.tlover.domain.myfile.service;

import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.myfile.entity.MyFile;
import com.example.tlover.domain.myfile.exception.NotFoundMyFileException;
import com.example.tlover.domain.myfile.service.repository.MyFileRepository;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.infra.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyFileServiceImpl implements MyFileService {

    private final FileService fileService;
    private final MyFileRepository myFileRepository;

    @Override
    public List<MyFile> saveImages(List<MultipartFile> multipartFiles) {
        List<MyFile> myFiles = new ArrayList<>();
        if(multipartFiles!=null)
            for (MultipartFile multipartFile : multipartFiles)
                myFiles.add(this.myFileRepository.save(MyFile.toEntity(this.fileService.saveImage(multipartFile))));
        return myFiles;
    }

    @Override
    public MyFile saveImage(MultipartFile multipartFile) {
        return this.myFileRepository.save(MyFile.toEntity(this.fileService.saveImage(multipartFile)));
    }

    @Override
    public MyFile getFile(Long fileId) {
        return this.myFileRepository.findOneNotDeletedByFileId(fileId).orElseThrow(NotFoundMyFileException::new);
    }

    @Override
    @Transactional
    public boolean deleteFile(Long fileId) {
        MyFile myFile = this.findByFileId(fileId);
        myFile.setDeleted(true);
        this.fileService.delete(myFile.getFileKey());
        return true;
    }

    private MyFile findByFileId(Long fileId) {
        return this.myFileRepository.findById(fileId).orElseThrow(NotFoundMyFileException::new);
    }

    @Override
    public List<MyFile> findByUserAndDiary(User user, Diary diary) {
        return this.myFileRepository.findByUserAndDiary(user , diary).orElseThrow(NotFoundMyFileException::new);
    }


}
