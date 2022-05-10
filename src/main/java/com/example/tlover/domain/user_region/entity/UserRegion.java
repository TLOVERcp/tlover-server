package com.example.tlover.domain.user_region.entity;


import com.example.tlover.domain.region.entity.Region;
import com.example.tlover.domain.user.entity.User;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegion {

    @Id
    @GeneratedValue(strategy = IDENTITY)
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

    public static UserRegion toEntityOfUserRegion(User user, Region region) {
        return UserRegion.builder()
                .user(user)
                .region(region)
                .build();
    }

}
