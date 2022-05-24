package com.example.tlover.domain.myfile.entity;

import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.plan.entity.Plan;
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
        @JoinColumn(name = "user_userId")
        private User user;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "diary_diaryId")
        private Diary diary;

        private boolean isDeleted;

        public void setUser(User user) {
        this.user = user;
        user.getMyFiles().add(this);
    }

    public void setDiary(Diary diary) {
        this.diary = diary;
        diary.getMyFiles().add(this);
    }

    public static MyFile toEntity(String key) {
     return MyFile.builder()
                .fileKey(key)
                .build();
    }
}
