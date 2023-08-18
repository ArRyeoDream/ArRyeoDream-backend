package com.kimbab.ArRyeoDream.lecture.repository;

import com.kimbab.ArRyeoDream.lecture.entity.ApplicationList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationListRepository extends JpaRepository<ApplicationList, Long> {
    List<ApplicationList> findAllByLectureId(Long lectureId);
}
