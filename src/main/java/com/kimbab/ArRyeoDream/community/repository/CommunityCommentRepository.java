package com.kimbab.ArRyeoDream.community.repository;

import com.kimbab.ArRyeoDream.community.entity.CommunityComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityCommentRepository extends JpaRepository<CommunityComment, Long> {
    List<CommunityComment> findAllByCommunityId(Long communityId);
    Long deleteAllByCommunityId(Long communityId);
    CommunityComment findByCommunityId(Long communityId);
}
