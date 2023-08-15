package com.kimbab.ArRyeoDream.community.repository;

import com.kimbab.ArRyeoDream.community.entity.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {
    Page<Community> findAllByOrderByIdDesc(Pageable pageable);
}
