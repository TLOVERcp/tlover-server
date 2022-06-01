package com.example.tlover.domain.weather.entity;

import com.example.tlover.domain.region.entity.Region;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Weather{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long weatherId;

    private String weatherRegion;

    private String weatherTciGrade;

    private double kmaTci;

    private String time;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_regionId")
    private Region region;
}
