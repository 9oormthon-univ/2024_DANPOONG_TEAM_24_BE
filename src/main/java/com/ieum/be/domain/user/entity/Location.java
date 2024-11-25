package com.ieum.be.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    @Getter
    @Id
    @Column(name = "location_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(nullable = false)
    private Integer priority;

    @Getter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Getter
    @Setter
    @Column(name = "latitude")
    private Double latitude;

    @Getter
    @Setter
    @Column(name = "longitude")
    private Double longitude;
}
