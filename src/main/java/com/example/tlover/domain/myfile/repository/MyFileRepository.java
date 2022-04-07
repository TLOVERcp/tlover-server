package com.example.tlover.domain.myfile.repository;

import com.example.tlover.domain.myfile.entity.MyFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyFileRepository extends JpaRepository<MyFile, Long>, MyFileRepositoryCustom {

}
