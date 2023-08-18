package com.kimbab.ArRyeoDream.lecture.service;

import com.kimbab.ArRyeoDream.common.dto.PageResult;
import com.kimbab.ArRyeoDream.common.error.BusinessException;
import com.kimbab.ArRyeoDream.common.error.ErrorCode;
import com.kimbab.ArRyeoDream.lecture.dto.*;
import com.kimbab.ArRyeoDream.lecture.entity.ApplicationList;
import com.kimbab.ArRyeoDream.lecture.entity.Lecture;
import com.kimbab.ArRyeoDream.lecture.entity.LectureComment;
import com.kimbab.ArRyeoDream.lecture.entity.LectureImage;
import com.kimbab.ArRyeoDream.lecture.repository.ApplicationListRepository;
import com.kimbab.ArRyeoDream.lecture.repository.LectureCommentRepository;
import com.kimbab.ArRyeoDream.lecture.repository.LectureImageRepository;
import com.kimbab.ArRyeoDream.lecture.repository.LectureRepository;
import com.kimbab.ArRyeoDream.user.entity.Attendee;
import com.kimbab.ArRyeoDream.user.entity.User;
import com.kimbab.ArRyeoDream.user.repository.AttendeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LectureServiceImpl implements LectureService {
    private final LectureRepository lectureRepository;
    private final LectureImageRepository lectureImageRepository;
    private final LectureCommentRepository lectureCommentRepository;
    private final AttendeeRepository attendeeRepository;
    private final ApplicationListRepository applicationListRepository;

    @Override
    public PageResult<LectureListResponseDTO> getLectureList(Pageable pageable, String sort){
        if(sort.equals("newest")){
            Page<Lecture> lectures = lectureRepository.findAllByOrderByIdDesc(pageable);
            PageResult<LectureListResponseDTO> result = PageResult.ok(lectures.map(lecture ->
                    lecture.toLectureListResponseDTO(
                            lecture,
                            lectureImageRepository.findAllByLectureId(lecture.getId()).get(0).getLink()
                    )
                )
            );
            return result;
        }
        else if(sort.equals("popular")){
            Page<Lecture> lectures = lectureRepository.findAllByOrderByViewsDesc(pageable);
            PageResult<LectureListResponseDTO> result = PageResult.ok(lectures.map(lecture ->
                            lecture.toLectureListResponseDTO(
                                    lecture,
                                    lectureImageRepository.findAllByLectureId(lecture.getId()).get(0).getLink()
                            )
                    )
            );
            return result;
        }
        else{
            throw new BusinessException(ErrorCode.BAD_REQUEST);
        }
    }

    @Override
    @Transactional
    public LectureResponseDTO getLectureDetail(Long id){
        Optional<Lecture> lectureById = lectureRepository.findById(id);
        if(lectureById.isPresent()){
            Lecture lecture = lectureById.get();
            lecture.setViews(lecture.getViews()+1L);
            LectureResponseDTO lectureResponseDTO = LectureResponseDTO.builder()
                    .lectureId(lecture.getId())
                    .title(lecture.getTitle())
                    .intro(lecture.getIntro())
                    .region(Arrays.asList(lecture.getRegion().trim().split("/")))
                    .week(Arrays.asList(lecture.getWeek().trim().split("/")))
                    .images(lectureImageRepository.findAllByLectureId(id).stream().map(LectureImageResponseDTO::new).collect(Collectors.toList()))
                    .comments(lectureCommentRepository.findAllByLectureId(id).stream().map(LectureCommentResponseDTO::new).collect(Collectors.toList()))
                    .views(lecture.getViews())
                    .createdAt(lecture.getCreatedAt())
                    .updatedAt(lecture.getUpdatedAt())
                    .build();
            return lectureResponseDTO;
        }
        else{
            throw new BusinessException(ErrorCode.LECTURE_NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public Long saveLecture(LectureRequestDTO lectureRequestDTO, User user){
        try {
            Lecture lecture = Lecture.builder()
                    .title(lectureRequestDTO.getTitle())
                    .intro(lectureRequestDTO.getIntro())
                    .region(regionArrayToString(lectureRequestDTO.getRegion()))
                    .week(weekArrayToString(lectureRequestDTO.getWeek()))
                    .views(0L)
                    .userId(user.getId())
                    .build();
            Lecture savedLecture = lectureRepository.save(lecture);
            
            // 이미지 신규 저장
            for (int i = 0; i < lectureRequestDTO.getImages().size(); i++) {
                LectureImage lectureImage = LectureImage.builder()
                        .link(lectureRequestDTO.getImages().get(i))
                        .lecture(savedLecture)
                        .build();
                lectureImageRepository.save(lectureImage);
            }
            return savedLecture.getId();
        } catch(Exception e){
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public Long updateLecture(Long id, LectureRequestDTO lectureRequestDTO, User user){
        Optional<Lecture> lecture = lectureRepository.findById(id);
        if(lecture.isPresent()){
            if(lecture.get().getUserId() != user.getId()){
                throw new BusinessException(ErrorCode.BAD_REQUEST);
            }
            Lecture lectureData = lecture.get();
            lectureData.setTitle(lectureRequestDTO.getTitle());
            lectureData.setIntro(lectureRequestDTO.getIntro());
            lectureData.setRegion(regionArrayToString(lectureRequestDTO.getRegion()));
            lectureData.setWeek(weekArrayToString(lectureRequestDTO.getWeek()));
            // 기존 이미지 삭제 후 저장
            lectureImageRepository.deleteAllByLectureId(lectureData.getId());
            for (int i = 0; i < lectureRequestDTO.getImages().size(); i++) {
                LectureImage lectureImage = LectureImage.builder()
                        .link(lectureRequestDTO.getImages().get(i))
                        .lecture(lectureData)
                        .build();
                lectureImageRepository.save(lectureImage);
            }
            return lectureData.getId();
        }
        else{
            throw new BusinessException(ErrorCode.LECTURE_NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public void deleteLecture(Long id, User user){
        Optional<Lecture> lecture = lectureRepository.findById(id);
        if(lecture.isPresent()){
            if(lecture.get().getUserId() != user.getId()){
                throw new BusinessException(ErrorCode.BAD_REQUEST);
            }
            lectureImageRepository.deleteAllByLectureId(id);
            lectureCommentRepository.deleteAllByLectureId(id);
            lectureRepository.delete(lecture.get());
        }
        else{
            throw new BusinessException(ErrorCode.LECTURE_NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public Long saveCommentInLecture(LectureCommentRequestDTO lectureCommentRequestDTO, Long id){
        try {
            Optional<Attendee> getAttendee = attendeeRepository.findByNameAndPhone(lectureCommentRequestDTO.getName(), lectureCommentRequestDTO.getPhone());
            if(!getAttendee.isPresent()){
                throw new BusinessException(ErrorCode.INVALID_ATTENDEE);
            }
            LectureComment comment = LectureComment.builder()
                    .lecture(lectureRepository.findById(id).get())
                    .content(lectureCommentRequestDTO.getContent())
                    .attendee(getAttendee.get())
                    .build();
            return lectureCommentRepository.save(comment).getId();
        } catch(Exception e){
            throw new BusinessException(ErrorCode.BAD_REQUEST);
        }
    }

    @Override
    @Transactional
    public Long updateCommentInLecture(LectureCommentRequestDTO lectureCommentRequestDTO, Long id){
        Optional<Attendee> attendee = attendeeRepository.findByNameAndPhone(lectureCommentRequestDTO.getName(), lectureCommentRequestDTO.getPhone());
        Optional<Lecture> lecture = lectureRepository.findById(id);
        if(lecture.isPresent()){
            try {
                LectureComment comment = lectureCommentRepository.findByLectureIdAndAttendeeId(lecture.get().getId(), attendee.get().getId());
                comment.setLecture(lecture.get());
                comment.setAttendee(attendee.get());
                comment.setContent(lectureCommentRequestDTO.getContent());
                return comment.getId();
            } catch(Exception e){
                throw new BusinessException(ErrorCode.ATTENDEE_NOT_FOUND);
            }
        }
        else {
            throw new BusinessException(ErrorCode.LECTURE_NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public void deleteCommentInLecture(Long id){
        try {
            lectureCommentRepository.delete(lectureCommentRepository.findById(id).get());
        } catch (Exception e){
            throw new BusinessException(ErrorCode.COMMENT_NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public Long applicationLecture(Long id, LectureApplicationRequestDTO lectureApplicationRequestDTO){
        try{
            Optional<Lecture> lecture = lectureRepository.findById(id);
            if(lecture.isPresent()) {
                Attendee attendee = Attendee.builder()
                        .name(lectureApplicationRequestDTO.getName())
                        .phone(lectureApplicationRequestDTO.getPhone())
                        .build();
                attendeeRepository.save(attendee);
                ApplicationList applicationList = ApplicationList.builder()
                        .lecture(lecture.get())
                        .attendee(attendeeRepository.save(attendee))
                        .build();
                return applicationListRepository.save(applicationList).getId();
            }
            else{
                throw new BusinessException(ErrorCode.LECTURE_NOT_FOUND);
            }
        } catch (Exception e){
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public String weekArrayToString(List<String> week){
        String weeks = "";
        for(int i = 0; i < week.size(); i++){
            weeks += week.get(i)+"/";
        }
        return weeks;
    }

    public String regionArrayToString(List<String> region){
        String regions = "";
        for(int i = 0; i < region.size(); i++){
            regions += region.get(i)+"/";
        }
        return regions;
    }
}
