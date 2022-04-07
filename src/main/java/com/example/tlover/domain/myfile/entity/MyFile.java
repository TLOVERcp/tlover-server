package com.example.tlover.domain.myfile.entity;

import com.example.tlover.domain.user.entity.User;
import com.example.tlover.global.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyFile extends BaseTimeEntity {

    @Setter(value = AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long myFileId;
    private String fileKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static MyFile from(String key) {
        return MyFile.builder()
                .fileKey(key)
                .build();
    }
}
