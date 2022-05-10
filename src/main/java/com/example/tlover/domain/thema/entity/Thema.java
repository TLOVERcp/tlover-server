package com.example.tlover.domain.thema.entity;

import com.example.tlover.domain.diary_thema.entity.DiaryThema;
import com.example.tlover.domain.user_thema.entitiy.UserThema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Thema {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long themaId;

    private String themaName;

    @OneToMany(mappedBy = "thema")
    private List<DiaryThema> diaryThemas = new ArrayList<>();

    @OneToMany(mappedBy = "thema")
    private List<UserThema> userThemas = new ArrayList<>();
}
