package site.haruhana.www.exception;

import lombok.Getter;
import site.haruhana.www.common.ErrorCode;

@Getter
public class ProblemNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public ProblemNotFoundException() {
        this(ErrorCode.PROBLEM_NOT_FOUND);
    }

    public ProblemNotFoundException(ErrorCode errorCode) {
        super(errorCode.getDefaultMessage());
        this.errorCode = errorCode;
    }
}