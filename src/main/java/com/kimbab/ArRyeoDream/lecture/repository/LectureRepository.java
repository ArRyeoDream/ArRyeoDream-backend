package com.kimbab.ArRyeoDream.lecture.repository;

import com.kimbab.ArRyeoDream.lecture.entity.Lecture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {
    Page<Lecture> findAllByOrderByIdDesc(Pageable pageable);
    Page<Lecture> findAllByOrderByViewsDesc(Pageable pageable);
}
