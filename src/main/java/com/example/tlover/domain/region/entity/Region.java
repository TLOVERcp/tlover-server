package com.example.tlover.domain.region.entity;

import com.example.tlover.domain.diary_region.entity.DiaryRegion;
import com.example.tlover.domain.plan_region.entity.PlanRegion;
import com.example.tlover.domain.user_region.entity.UserRegion;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Region {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long regionId;

    private String regionName;

    @OneToMany(mappedBy = "region")
    private List<UserRegion> userRegions = new ArrayList<>();

    @OneToMany(mappedBy = "region")
    private List<DiaryRegion> diaryRegions = new ArrayList<>();

    @OneToMany(mappedBy = "region")
    private List<PlanRegion> planRegions = new ArrayList<>();

}
