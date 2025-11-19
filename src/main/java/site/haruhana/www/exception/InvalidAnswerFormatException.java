package site.haruhana.www.exception;

import lombok.Getter;
import site.haruhana.www.common.ErrorCode;

@Getter
public class InvalidAnswerFormatException extends RuntimeException {
    private final ErrorCode errorCode;

    public InvalidAnswerFormatException() {
        this(ErrorCode.INVALID_ANSWER_FORMAT);
    }

    public InvalidAnswerFormatException(ErrorCode errorCode) {
        super(errorCode.getDefaultMessage());
        this.errorCode = errorCode;
    }
}
