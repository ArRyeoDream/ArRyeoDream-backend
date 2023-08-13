package com.kimbab.ArRyeoDream.lecture.repository;

import com.kimbab.ArRyeoDream.lecture.entity.LectureComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureCommentRepository extends JpaRepository<LectureComment, Long> {
    List<LectureComment> findAllByLectureId(Long lectureId);
    LectureComment findByLectureIdAndAttendeeId(Long lectureId, Long attendeeId);
}
