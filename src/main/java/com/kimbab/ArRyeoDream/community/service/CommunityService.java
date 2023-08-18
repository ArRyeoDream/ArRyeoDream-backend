package com.kimbab.ArRyeoDream.community.service;

import com.kimbab.ArRyeoDream.common.dto.PageResult;
import com.kimbab.ArRyeoDream.community.dto.CommunityCommentRequestDTO;
import com.kimbab.ArRyeoDream.community.dto.CommunityListResponseDTO;
import com.kimbab.ArRyeoDream.community.dto.CommunityRequestDTO;
import com.kimbab.ArRyeoDream.community.dto.CommunityResponseDTO;
import com.kimbab.ArRyeoDream.user.entity.User;
import org.springframework.data.domain.Pageable;

public interface CommunityService {
    PageResult<CommunityListResponseDTO> getCommunityList(Pageable pageable);
    CommunityResponseDTO getCommunityDetail(Long id);
    Long saveCommunity(CommunityRequestDTO communityRequestDTO, User user);
    Long updateCommunity(Long id, CommunityRequestDTO communityRequestDTO, User user);
    void deleteCommunity(Long id, User user);

    //댓글 관련
    Long saveCommentInCommunity(CommunityCommentRequestDTO communityCommentRequestDTO, Long id, User user);
    Long updateCommentInCommunity(CommunityCommentRequestDTO communityCommentRequestDTO, Long id, User user);
    void deleteCommentInCommunity(Long id, User user);
}
