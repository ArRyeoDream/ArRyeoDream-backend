package com.kimbab.ArRyeoDream.community.service;

import com.kimbab.ArRyeoDream.common.dto.PageResult;
import com.kimbab.ArRyeoDream.common.error.BusinessException;
import com.kimbab.ArRyeoDream.common.error.ErrorCode;
import com.kimbab.ArRyeoDream.community.dto.*;
import com.kimbab.ArRyeoDream.community.entity.Community;
import com.kimbab.ArRyeoDream.community.entity.CommunityComment;
import com.kimbab.ArRyeoDream.community.entity.CommunityImage;
import com.kimbab.ArRyeoDream.community.repository.CommunityCommentRepository;
import com.kimbab.ArRyeoDream.community.repository.CommunityImageRepository;
import com.kimbab.ArRyeoDream.community.repository.CommunityRepository;
import com.kimbab.ArRyeoDream.user.entity.User;
import com.kimbab.ArRyeoDream.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService{
    private final CommunityRepository communityRepository;
    private final CommunityImageRepository communityImageRepository;
    private final CommunityCommentRepository communityCommentRepository;
    private final UserRepository userRepository;

    @Override
    public PageResult<CommunityListResponseDTO> getCommunityList(Pageable pageable){
        try {
            Page<Community> communityPage = communityRepository.findAllByOrderByIdDesc(pageable);
            PageResult<CommunityListResponseDTO> result = PageResult.ok(communityPage.map(community ->
                            community.toCommunityListResponseDTO(community)
                    )
            );
            return result;
        } catch (Exception e){
            throw new BusinessException(ErrorCode.COMMUNITY_NOT_FOUND);
        }
    }

    @Override
    public CommunityResponseDTO getCommunityDetail(Long id){
        Optional<Community> communityOptional = communityRepository.findById(id);
        if(communityOptional.isPresent()){
            Community community = communityOptional.get();
            CommunityResponseDTO communityResponseDTO = CommunityResponseDTO.builder()
                    .id(community.getId())
                    .title(community.getTitle())
                    .content(community.getContent())
                    .nickname("유저 개발 완료 이후 추가")
                    .images(communityImageRepository.findAllByCommunityId(id).stream().map(CommunityImageResponseDTO::new).collect(Collectors.toList()))
                    .comments(communityCommentRepository.findAllByCommunityId(id).stream().map(CommunityCommentResponseDTO::new).collect(Collectors.toList()))
                    .createdAt(community.getCreatedAt())
                    .updatedAt(community.getUpdatedAt())
                    .build();
            return communityResponseDTO;
        }
        else{
            throw new BusinessException(ErrorCode.COMMUNITY_NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public Long saveCommunity(CommunityRequestDTO communityRequestDTO, User user){
        try {
            Community community = Community.builder()
                    .title(communityRequestDTO.getTitle())
                    .content(communityRequestDTO.getContent())
                    .userId(user.getId())
                    .build();
            Community savedCommunity = communityRepository.save(community);

            // 이미지 신규 저장
            for (int i = 0; i < communityRequestDTO.getImages().size(); i++) {
                CommunityImage communityImage = CommunityImage.builder()
                        .link(communityRequestDTO.getImages().get(i))
                        .community(savedCommunity)
                        .build();
                communityImageRepository.save(communityImage);
            }
            return savedCommunity.getId();
        } catch(Exception e){
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public Long updateCommunity(Long id, CommunityRequestDTO communityRequestDTO, User user){
        Optional<Community> community = communityRepository.findById(id);
        if(community.isPresent()){
            if(community.get().getUserId() != user.getId()){
                throw new BusinessException(ErrorCode.BAD_REQUEST);
            }
            Community communityData = community.get();
            communityData.setTitle(communityRequestDTO.getTitle());
            communityData.setContent(communityRequestDTO.getContent());
            // 기존 이미지 삭제 후 저장
            communityImageRepository.deleteAllByCommunityId(communityData.getId());
            for (int i = 0; i < communityRequestDTO.getImages().size(); i++) {
                CommunityImage communityImage = CommunityImage.builder()
                        .link(communityRequestDTO.getImages().get(i))
                        .community(communityData)
                        .build();
                communityImageRepository.save(communityImage);
            }
            return communityData.getId();
        }
        else{
            throw new BusinessException(ErrorCode.LECTURE_NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public void deleteCommunity(Long id, User user){
        Optional<Community> community = communityRepository.findById(id);
        if(community.isPresent()){
            if(community.get().getUserId() != user.getId()){
                throw new BusinessException(ErrorCode.BAD_REQUEST);
            }
            communityImageRepository.deleteAllByCommunityId(id);
            communityCommentRepository.deleteAllByCommunityId(id);
            communityRepository.delete(community.get());
        }
        else{
            throw new BusinessException(ErrorCode.COMMUNITY_NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public Long saveCommentInCommunity(CommunityCommentRequestDTO communityCommentRequestDTO, Long id, User user){
        try {
            CommunityComment comment = CommunityComment.builder()
                    .community(communityRepository.findById(id).get())
                    .content(communityCommentRequestDTO.getContent())
                    .userId(user.getId())
                    .build();
            return communityCommentRepository.save(comment).getId();
        } catch(Exception e){
            throw new BusinessException(ErrorCode.BAD_REQUEST);
        }
    }

    @Override
    @Transactional
    public Long updateCommentInCommunity(CommunityCommentRequestDTO communityCommentRequestDTO, Long id, User user){
        Optional<Community> community = communityRepository.findById(id);
        if(community.isPresent()){
            try {
                if(community.get().getUserId() != user.getId()){
                    throw new BusinessException(ErrorCode.BAD_REQUEST);
                }
                CommunityComment comment = communityCommentRepository.findByCommunityId(id);
                comment.setCommunity(community.get());
                comment.setContent(communityCommentRequestDTO.getContent());
                comment.setUserId(0L);
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
    public void deleteCommentInCommunity(Long id, User user){
        try {
            if(communityCommentRepository.findById(id).get().getUserId() != user.getId()){
                throw new BusinessException(ErrorCode.BAD_REQUEST);
            }
            communityCommentRepository.delete(communityCommentRepository.findById(id).get());
        } catch (Exception e){
            throw new BusinessException(ErrorCode.COMMENT_NOT_FOUND);
        }
    }

}
