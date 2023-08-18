package com.kimbab.ArRyeoDream.mypage.service;

import com.kimbab.ArRyeoDream.mypage.dto.MyCommunityListResponseDTO;
import com.kimbab.ArRyeoDream.mypage.dto.MyLectureListResponseDTO;
import com.kimbab.ArRyeoDream.user.entity.User;

public interface MyPageService {
    MyLectureListResponseDTO getMyLectureList(User user);
    MyCommunityListResponseDTO getMyCommunityList(User user);
}
