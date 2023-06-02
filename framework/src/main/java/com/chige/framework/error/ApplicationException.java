package com.chige.framework.error;

/**
 * @author wangyc
 * @date 2023/4/21
 */
public class ApplicationException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private ErrorContext errorContext;

    public ApplicationException(String errorMsg) {
        this(new CommonError(-1, errorMsg));
    }

    public ApplicationException(CommonError error) {
        this((ErrorContext) (new ErrorContext(error)), (Throwable) null);
    }

    public ApplicationException(CommonError error, Throwable t) {
        this(new ErrorContext(error), t);
    }

    public ApplicationException(ErrorContext errorContext) {
        this((ErrorContext) errorContext, (Throwable) null);
    }

    public ApplicationException(ErrorContext errorContext, Throwable t) {
        super(errorContext.fetchCurrentError() == null ? null : errorContext.fetchCurrentError().getErrorDesc(), t);
        this.errorContext = errorContext;
    }

    public ErrorContext getErrorContext() {
        return this.errorContext;
    }

}
