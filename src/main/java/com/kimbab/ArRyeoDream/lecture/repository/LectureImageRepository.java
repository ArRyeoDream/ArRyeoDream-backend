package com.kimbab.ArRyeoDream.lecture.repository;

import com.kimbab.ArRyeoDream.lecture.entity.LectureImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureImageRepository extends JpaRepository<LectureImage, Long> {
    List<LectureImage> findAllByLectureId(Long lectureId);
    Long deleteAllByLectureId(Long lectureId);
}
