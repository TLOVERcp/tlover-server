package com.example.tlover.global.init.factory;

import com.example.tlover.domain.myfile.entity.MyFile;
import com.example.tlover.domain.myfile.service.MyFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class MyFileFactory {

    private final MyFileService myFileService;
    private static final ClassLoader CLASS_LOADER = MyFileFactory.class.getClassLoader();
    private static final String FILE_KEY = "image";
    private static final String FILE_PATH = "images/";
    public List<MyFile> createMyFileLists() throws IOException {
        MultipartFile multipartFile0 = fileToMultipart(createFile(FILE_PATH+"autem.png"));
        MultipartFile multipartFile1 = fileToMultipart(createFile(FILE_PATH+"deleniti.png"));
        MultipartFile multipartFile2 = fileToMultipart(createFile(FILE_PATH+"dignissimos.png"));
        MultipartFile multipartFile3 = fileToMultipart(createFile(FILE_PATH+"estsit1.png"));
        MultipartFile multipartFile4 = fileToMultipart(createFile(FILE_PATH+"estsit2-1.png"));
        MultipartFile multipartFile5 = fileToMultipart(createFile(FILE_PATH+"estsit2-2.png"));
        MultipartFile multipartFile6 = fileToMultipart(createFile(FILE_PATH+"estsit2-3.png"));
        MultipartFile multipartFile7 = fileToMultipart(createFile(FILE_PATH+"gyeongbokgoong.png"));
        MultipartFile multipartFile8 = fileToMultipart(createFile(FILE_PATH+"impedit.png"));
        MultipartFile multipartFile9 = fileToMultipart(createFile(FILE_PATH+"namsan.png"));
        MultipartFile multipartFile10 = fileToMultipart(createFile(FILE_PATH+"impedit.png"));
        MultipartFile multipartFile11 = fileToMultipart(createFile(FILE_PATH+"sitdolores.png"));
        MultipartFile multipartFile12 = fileToMultipart(createFile(FILE_PATH+"sitdoloresquos.png"));
        MultipartFile multipartFile13 = fileToMultipart(createFile(FILE_PATH+"sitdoloresquosquo.png"));
        MultipartFile multipartFile14 = fileToMultipart(createFile(FILE_PATH+"tenetur.png"));
        List<MultipartFile> multipartFileList = List.of(multipartFile0, multipartFile1, multipartFile2, multipartFile3, multipartFile4, multipartFile5, multipartFile6,
                multipartFile7, multipartFile8, multipartFile9, multipartFile10, multipartFile11, multipartFile12, multipartFile13,
                multipartFile14);
        List<MyFile> myFiles = this.myFileService.saveImages(multipartFileList);
        return myFiles;
    }

    private MultipartFile fileToMultipart(File file){
        try {
            return new MockMultipartFile(
                    FILE_KEY,
                    file.getName(),
                    ContentType.IMAGE_PNG.getMimeType(),
                    Files.readAllBytes(file.toPath())
            );
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private File createFile(String fileName) {
        URL resource = CLASS_LOADER.getResource(fileName);
        System.out.println("resource"+resource);
        Objects.requireNonNull(resource);
        return new File(resource.getFile());
    }
}
