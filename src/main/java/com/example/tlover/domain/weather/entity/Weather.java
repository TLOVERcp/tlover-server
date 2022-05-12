package com.example.tlover.domain.weather.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long weatherId;

    private String weatherRegion;

    private String weatherTciGrade;

    private double kmaTci;

    private String time;
}
