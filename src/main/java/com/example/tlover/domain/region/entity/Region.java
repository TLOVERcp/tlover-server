package com.example.tlover.domain.region.entity;

import com.example.tlover.domain.annonce_region.entity.AnnonceRegion;
import com.example.tlover.domain.plan_region.entity.PlanRegion;
import com.example.tlover.domain.user_region.entity.UserRegion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Region {

    @Id
    @GeneratedValue
    private Long regionId;

    private Long regionName;

    @OneToMany(mappedBy = "region")
    private List<UserRegion> userRegions = new ArrayList<>();

    @OneToMany(mappedBy = "region")
    private List<AnnonceRegion> annonceRegions = new ArrayList<>();

    @OneToMany(mappedBy = "region")
    private List<PlanRegion> planRegions = new ArrayList<>();
}
