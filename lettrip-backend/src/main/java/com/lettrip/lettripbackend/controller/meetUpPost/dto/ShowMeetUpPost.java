package com.lettrip.lettripbackend.controller.meetUpPost.dto;

import com.lettrip.lettripbackend.constant.Province;
import com.lettrip.lettripbackend.controller.user.dto.UserDto;
import com.lettrip.lettripbackend.domain.meetup.MeetUpPost;
import lombok.*;

import java.time.LocalDateTime;

public class ShowMeetUpPost {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {
        Long id;
        UserDto.Response userDto;
        Boolean isGPSEnabled;
        LocalDateTime meetUpDate;
        Province province;
        String city;
        String title;
        String content;
        LocalDateTime createdDate;
        Long placeId;

        Long travelId;

        public static Response fromEntity(MeetUpPost meetUpPost) {
            return Response.builder()
                    .id(meetUpPost.getId())
                    .userDto(
                            new UserDto.Response(meetUpPost.getUser())
                    )
                    .isGPSEnabled(meetUpPost.isGPSEnabled())
                    .meetUpDate(meetUpPost.getMeetUpDate())
                    .province(meetUpPost.getProvince())
                    .city(meetUpPost.getCity())
                    .title(meetUpPost.getTitle())
                    .content(meetUpPost.getContent())
                    .createdDate(meetUpPost.getCreatedDate())
                    .placeId(meetUpPost.getId())
                    .travelId(meetUpPost.getId())
                    .build();
        }
    }
}
