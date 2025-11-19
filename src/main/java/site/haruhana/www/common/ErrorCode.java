package site.haruhana.www.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import site.haruhana.www.dto.BaseResponse;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // ====== 성공 코드 ======
    PROBLEM_LIST_FETCH_SUCCESS(HttpStatus.OK, "문제 목록을 조회하는데 성공했습니다."),
    PROBLEM_FETCH_SUCCESS(HttpStatus.OK, "문제를 조회하는데 성공했습니다."),
    SUBMISSION_CREATED(HttpStatus.OK, "답안이 제출되었습니다."), // 원하면 CREATED로 바꿔도 됨
    SUBMISSION_HISTORY_FETCH_SUCCESS(HttpStatus.OK, "문제 풀이 기록이 성공적으로 조회되었습니다."),
    TOKEN_REFRESH_SUCCESS(HttpStatus.OK, "토큰이 성공적으로 갱신되었습니다."),
    CURRENT_USER_FETCH_SUCCESS(HttpStatus.OK, "현재 사용자 정보를 성공적으로 조회했습니다."),

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

    // data 있는 케이스 (주로 성공)
    public <T> BaseResponse<T> toResponse(T data) {
        return new BaseResponse<>(status.value(), defaultMessage, data);
    }

    // data 없는 케이스 (주로 에러)
    public BaseResponse<Void> toResponse() {
        return new BaseResponse<>(status.value(), defaultMessage, null);
    }

    // 메시지 override (기본 + 상세 붙이거나 완전 교체하고 싶을 때)
    public <T> BaseResponse<T> toResponse(String overrideMessage, T data) {
        String msg = (overrideMessage != null) ? overrideMessage : defaultMessage;
        return new BaseResponse<>(status.value(), msg, data);
    }

    public BaseResponse<Void> toResponse(String overrideMessage) {
        String msg = (overrideMessage != null) ? overrideMessage : defaultMessage;
        return new BaseResponse<>(status.value(), msg, null);
    }

    // ====== ResponseEntity까지 한 번에 만들고 싶을 때 ======

    public <T> ResponseEntity<BaseResponse<T>> toResponseEntity(T data) {
        return ResponseEntity.status(status).body(toResponse(data));
    }

    public ResponseEntity<BaseResponse<Void>> toResponseEntity() {
        return ResponseEntity.status(status).body(toResponse());
    }

    public <T> ResponseEntity<BaseResponse<T>> toResponseEntity(String overrideMessage, T data) {
        return ResponseEntity.status(status).body(toResponse(overrideMessage, data));
    }

    public ResponseEntity<BaseResponse<Void>> toResponseEntity(String overrideMessage) {
        return ResponseEntity.status(status).body(toResponse(overrideMessage));
    }

    // ====== 원하면 이런 static 헬퍼도 쓸 수 있음 (선택) ======
    public static <T> BaseResponse<T> success(ErrorCode code, T data) {
        return code.toResponse(data);
    }
}
