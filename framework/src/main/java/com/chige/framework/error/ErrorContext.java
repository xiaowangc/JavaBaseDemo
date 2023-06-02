package com.chige.framework.error;

import com.chige.framework.error.CommonError;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangyc
 * @date 2023/4/21
 */
public class ErrorContext {
    private static final long serialVersionUID = 1L;
    private List<CommonError> errorStack = new ArrayList();

    public ErrorContext() {
    }

    public ErrorContext(CommonError error) {
        this.addError(error);
    }

    public void addError(CommonError error) {
        this.errorStack.add(error);
    }

    public CommonError fetchCurrentError() {
        return CollectionUtils.isEmpty(this.errorStack) ? null : (CommonError) this.errorStack.get(this.errorStack.size() - 1);
    }

    public CommonError fetchRootError() {
        return CollectionUtils.isEmpty(this.errorStack) ? null : (CommonError) this.errorStack.get(0);
    }

    public List<CommonError> getErrorStack() {
        return this.errorStack;
    }
}
