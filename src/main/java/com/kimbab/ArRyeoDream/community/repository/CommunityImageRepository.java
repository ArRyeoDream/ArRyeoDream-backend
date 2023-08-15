package com.kimbab.ArRyeoDream.community.repository;

import com.kimbab.ArRyeoDream.community.entity.CommunityImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityImageRepository extends JpaRepository<CommunityImage, Long> {
    List<CommunityImage> findAllByCommunityId(Long communityId);
    Long deleteAllByCommunityId(Long communityID);
}
