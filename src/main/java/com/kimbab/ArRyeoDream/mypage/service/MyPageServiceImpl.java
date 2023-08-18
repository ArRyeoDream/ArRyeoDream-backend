package com.kimbab.ArRyeoDream.mypage.service;

import com.kimbab.ArRyeoDream.community.dto.CommunityListResponseDTO;
import com.kimbab.ArRyeoDream.community.entity.Community;
import com.kimbab.ArRyeoDream.community.repository.CommunityRepository;
import com.kimbab.ArRyeoDream.lecture.dto.LectureImageResponseDTO;
import com.kimbab.ArRyeoDream.lecture.entity.Lecture;
import com.kimbab.ArRyeoDream.lecture.repository.ApplicationListRepository;
import com.kimbab.ArRyeoDream.lecture.repository.LectureImageRepository;
import com.kimbab.ArRyeoDream.lecture.repository.LectureRepository;
import com.kimbab.ArRyeoDream.mypage.dto.MyCommunityListResponseDTO;
import com.kimbab.ArRyeoDream.mypage.dto.MyLectureListDTO;
import com.kimbab.ArRyeoDream.mypage.dto.MyLectureListResponseDTO;
import com.kimbab.ArRyeoDream.user.entity.Attendee;
import com.kimbab.ArRyeoDream.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService{
    private final LectureRepository lectureRepository;
    private final LectureImageRepository lectureImageRepository;
    private final CommunityRepository communityRepository;
    private final ApplicationListRepository applicationListRepository;

    @Override
    public MyLectureListResponseDTO getMyLectureList(User user){
        List<Lecture> lectures = lectureRepository.findAllByUserId(user.getId());
        List<MyLectureListDTO> myLectureListDTOs = lectures.stream()
                .map(m -> new MyLectureListDTO(
                        m.getId(),
                        m.getTitle(),
                        Arrays.asList(m.getRegion().trim().split("/")),
                        user.getNickname(),
                        lectureImageRepository.findAllByLectureId(m.getId()).get(0).getLink(),
                        m.getCreatedAt(),
                        m.getUpdatedAt(),
                        applicationListRepository.findAllByLectureId(m.getId()).stream()
                                .map(m2 -> new Attendee(
                                        m2.getAttendee().getId(),
                                        m2.getAttendee().getName(),
                                        m2.getAttendee().getPhone()
                                )).collect(Collectors.toList())
                )).collect(Collectors.toList());
        return new MyLectureListResponseDTO(myLectureListDTOs);
    }

    @Override
    public MyCommunityListResponseDTO getMyCommunityList(User user){
        List<Community> communityList = communityRepository.findAllByUserId(user.getId());
        List<CommunityListResponseDTO> communityListResponseDTOList = communityList.stream()
                .map(m -> new CommunityListResponseDTO(
                        m.getId(),
                        m.getTitle(),
                        m.getContent(),
                        m.getCreatedAt(),
                        m.getUpdatedAt()
                )).collect(Collectors.toList());
        return new MyCommunityListResponseDTO(communityListResponseDTOList);
    }
}
