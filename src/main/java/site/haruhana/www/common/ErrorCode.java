package site.haruhana.www.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import site.haruhana.www.dto.BaseResponse;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // ====== 공통 에러 코드 ======
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "입력값 검증에 실패했습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다. 로그인 후 이용해주세요."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    PROBLEM_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 문제입니다."),
    INVALID_ANSWER_FORMAT(HttpStatus.BAD_REQUEST, "올바르지 않은 답안 형식입니다. 객관식 문제의 경우 숫자만 입력 가능합니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    TOKEN_UNSUPPORTED(HttpStatus.UNAUTHORIZED, "지원되지 않는 형식의 토큰입니다."),
    TOKEN_MALFORMED(HttpStatus.UNAUTHORIZED, "잘못된 형식의 토큰입니다."),
    TOKEN_ERROR(HttpStatus.UNAUTHORIZED, "인증 토큰 처리 중 오류가 발생했습니다."),
    ILLEGAL_ARGUMENT(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."),
    SUBMISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "제출 정보가 존재하지 않습니다.");



    private final HttpStatus status;
    private final String defaultMessage;

    // ====== BaseResponse 생성 헬퍼 ======

    public BaseResponse<Void> toResponse() {
        return new BaseResponse<>(
                false,
                status.value(),
                defaultMessage,
                null
        );
    }

    public BaseResponse<Void> toResponse(String overrideMessage) {
        String msg = (overrideMessage != null) ? overrideMessage : defaultMessage;
        return new BaseResponse<>(
                false,
                status.value(),
                msg,
                null
        );
    }

    public ResponseEntity<BaseResponse<Void>> toResponseEntity() {
        return ResponseEntity
                .status(status)
                .body(toResponse());
    }

    public ResponseEntity<BaseResponse<Void>> toResponseEntity(String overrideMessage) {
        return ResponseEntity
                .status(status)
                .body(toResponse(overrideMessage));
    }
}
