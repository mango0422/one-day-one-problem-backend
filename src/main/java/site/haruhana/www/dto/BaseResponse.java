package site.haruhana.www.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import site.haruhana.www.common.ErrorCode;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "statusCode", "message", "data"})
@ToString
public class BaseResponse<T> {

    private Boolean isSuccess;

    private int statusCode;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static <T> BaseResponse<T> onSuccess(String message, T data) {
        return new BaseResponse<>(true, 200, message, data);
    }

    public static <T> BaseResponse<T> onUnauthorized(String message) {
        return new BaseResponse<>(false, 401, message, null);
    }

    public static <T> BaseResponse<T> onForbidden(String message) {
        return new BaseResponse<>(false, 403, message, null);
    }

    public static <T> BaseResponse<T> error(ErrorCode errorCode) {
        return new BaseResponse<>(false, errorCode.getStatus().value(), errorCode.getDefaultMessage(), null);
    }

    public static <T> BaseResponse<T> error(ErrorCode errorCode, String overrideMessage) {
        return new BaseResponse<>(false,
                errorCode.getStatus().value(),
                overrideMessage != null ? overrideMessage : errorCode.getDefaultMessage(),
                null
        );
    }

}