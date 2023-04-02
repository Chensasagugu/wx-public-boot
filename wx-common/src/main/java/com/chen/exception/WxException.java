package com.chen.exception;

import lombok.Getter;

/**
 * @author chen
 * @date 2023.03.31 11:38
 */
@Getter
public class WxException extends RuntimeException{
    private final WxErrorCode wxErrorCode;
    private final Object[] args;
    private Object data;

    public WxException(WxErrorCode assetErrorCode, Object... args) {
        this.wxErrorCode = assetErrorCode;
        this.args = args;
    }

    public WxException setData(Object data) {
        this.data = data;
        return this;
    }
}
