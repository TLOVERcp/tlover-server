package com.example.tlover.domain.annonce_region.entity;

import com.example.tlover.domain.annonce.entity.Annonce;
import com.example.tlover.domain.region.entity.Region;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnnonceRegion {

    @Id
    @GeneratedValue
    private Long annonceRegionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "annonce_annonceId")
    private Annonce annonce;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_regionId")
    private Region region;

    public void setAnnonce(Annonce annonce) {
        this.annonce = annonce;
        annonce.getAnnonceRegions().add(this);
    }

    public void setRegion(Region region) {
        this.region = region;
        region.getAnnonceRegions().add(this);
    }


}
