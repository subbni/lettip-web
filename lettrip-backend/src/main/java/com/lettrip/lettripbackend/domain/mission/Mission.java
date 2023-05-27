package com.lettrip.lettripbackend.domain.mission;

import com.lettrip.lettripbackend.constant.MissionType;
import com.lettrip.lettripbackend.domain.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Mission {
    @Id
    @Column(name="MISSION_ID")
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID")
    private User user;

    @Enumerated(value=EnumType.STRING)
    private MissionType missionType;

    private LocalDate accomplishedDate;

    @Builder
    public Mission(User user, MissionType missionType, LocalDate accomplishedDate) {
        this.user = user;
        this.missionType = missionType;
        this.accomplishedDate = accomplishedDate;
    }
}
