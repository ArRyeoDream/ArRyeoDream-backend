package com.kimbab.ArRyeoDream.community.service;

import com.kimbab.ArRyeoDream.common.dto.PageResult;
import com.kimbab.ArRyeoDream.community.dto.CommunityCommentRequestDTO;
import com.kimbab.ArRyeoDream.community.dto.CommunityListResponseDTO;
import com.kimbab.ArRyeoDream.community.dto.CommunityRequestDTO;
import com.kimbab.ArRyeoDream.community.dto.CommunityResponseDTO;
import org.springframework.data.domain.Pageable;

public interface CommunityService {
    PageResult<CommunityListResponseDTO> getCommunityList(Pageable pageable);
    CommunityResponseDTO getCommunityDetail(Long id);
    Long saveCommunity(CommunityRequestDTO communityRequestDTO);
    Long updateCommunity(Long id, CommunityRequestDTO communityRequestDTO);
    void deleteCommunity(Long id);

    //댓글 관련
    Long saveCommentInCommunity(CommunityCommentRequestDTO communityCommentRequestDTO, Long id);
    Long updateCommentInCommunity(CommunityCommentRequestDTO communityCommentRequestDTO, Long id);
    void deleteCommentInCommunity(Long id);
}
