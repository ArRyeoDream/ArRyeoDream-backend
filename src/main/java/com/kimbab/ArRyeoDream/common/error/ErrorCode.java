package com.kimbab.ArRyeoDream.common.error;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    //common
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생하였습니다."),
    //lecture
    LECTURE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 강좌를 찾을 수 없습니다."),
    //attendee
    ATTENDEE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 비회원 유저를 찾을 수 없습니다."),
    //comment
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 댓글입니다."),
    INVALID_ATTENDEE(HttpStatus.BAD_REQUEST, "강좌당 댓글은 1개씩만 달 수 있습니다.")
    ;


    private final HttpStatus status;
    private final String message;
}
