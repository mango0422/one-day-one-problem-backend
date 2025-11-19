package site.haruhana.www.exception;

import lombok.Getter;
import site.haruhana.www.common.ErrorCode;

@Getter
public class SubmissionNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public SubmissionNotFoundException() {
        this(ErrorCode.SUBMISSION_NOT_FOUND);
    }

    public SubmissionNotFoundException(ErrorCode errorCode) {
        super(errorCode.getDefaultMessage());
        this.errorCode = errorCode;
    }
}
