package com.example.tlover.domain.user_region.entity;


import com.example.tlover.domain.region.entity.Region;
import com.example.tlover.domain.user.entity.User;
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
public class UserRegion {

    @Id
    @GeneratedValue
    private Long userRegionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_regionId")
    private Region region;

    public void setUser(User user) {
        this.user = user;
        user.getUserRegions().add(this);
    }

    public void setRegion(Region region) {
        this.region = region;
        region.getUserRegions().add(this);
    }
}
