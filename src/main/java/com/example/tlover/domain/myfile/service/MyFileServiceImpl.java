package com.example.tlover.domain.myfile.service;

import com.example.tlover.domain.myfile.entity.MyFile;
import com.example.tlover.domain.myfile.repository.MyFileRepository;
import com.example.tlover.infra.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
                myFiles.add(this.myFileRepository.save(MyFile.from(this.fileService.saveImage(multipartFile))));
        return myFiles;
    }

    @Override
    public MyFile saveImage(MultipartFile multipartFile) {
        return this.myFileRepository.save(MyFile.from(this.fileService.saveImage(multipartFile)));
    }

}
