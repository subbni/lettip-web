package com.lettrip.lettripbackend.service;

import com.lettrip.lettripbackend.constant.Province;
import com.lettrip.lettripbackend.controller.ApiResponse;
import com.lettrip.lettripbackend.controller.meetUpPost.dto.CreateMeetUpPost;
import com.lettrip.lettripbackend.controller.meetUpPost.dto.ShowMeetUpPost;
import com.lettrip.lettripbackend.controller.meetUpPost.dto.ShowMeetUpPostList;
import com.lettrip.lettripbackend.domain.meetup.MeetUpPost;
import com.lettrip.lettripbackend.domain.user.User;
import com.lettrip.lettripbackend.exception.ResourceNotFoundException;
import com.lettrip.lettripbackend.repository.MeetUpPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MeetUpPostService {
    private final UserService userService;
    private final MeetUpPostRepository meetUpPostRepository;

    @Transactional
    public ApiResponse saveMeetUpPost(CreateMeetUpPost.Request request, Long userId) {
        User user = userService.findUserById(userId);
        MeetUpPost meetUpPost = meetUpPostRepository.save(
                MeetUpPost.builder()
                        .user(user)
                        .meetUpDate(request.getMeetUpDate())
                        .province(
                                Province.of(request.getProvince())
                        )
                        .city(
                                Province.getCityInProvince(
                                        Province.of(request.getProvince()), request.getCity()
                                )
                        )
                        .title(request.getTitle())
                        .content(request.getContent())
                        .build()
        );
        return new ApiResponse(true,"등록이 완료되었습니다.",meetUpPost.getId());
    }

    @Transactional
    public ApiResponse deleteMeetUpPost(Long meetUpPostId, Long userId) {
        MeetUpPost meetUpPost = findMeetUpPostById(meetUpPostId);
        checkIfWriter(meetUpPost, userId);
        meetUpPostRepository.delete(meetUpPost);
        return new ApiResponse(true,"삭제가 완료되었습니다.");
    }

    public ShowMeetUpPost.Response showMeetUpPost(Long meetUpPostId) {
        return ShowMeetUpPost.Response.fromEntity(
                findMeetUpPostById(meetUpPostId)
        );
    }

    public MeetUpPost findMeetUpPostById(Long meetUpPostId) {
        return meetUpPostRepository.findById(meetUpPostId)
                .orElseThrow(()-> {
                    throw new ResourceNotFoundException("MeetUpPost","meetUpPostId", meetUpPostId);
                });
    }

    public Page<ShowMeetUpPostList.Response> getAllMeetUpPostPage(Pageable pageable) {
        Page<MeetUpPost> page = meetUpPostRepository.findAll(pageable);
        return new PageImpl<ShowMeetUpPostList.Response>(
                meetUpPostToDto(page.getContent()),
                pageable,
                page.getTotalElements()
        );
    }

    private List<ShowMeetUpPostList.Response> meetUpPostToDto(List<MeetUpPost> meetUpPostList) {
        return meetUpPostList.stream()
                .map(ShowMeetUpPostList.Response::fromEntity)
                .collect(Collectors.toList());
    }
    private void checkIfWriter(MeetUpPost meetUpPost, Long userId) {
        if(meetUpPost.getUser().getId()!= userId) {
            throw new SecurityException("작성자만 가능한 작업입니다.");
        }
    }

}
