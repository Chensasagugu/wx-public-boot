package com.chen.exception;

import lombok.Getter;

@Getter
public enum WxErrorCode {
    ;

    private final int code;
    private final String msg;

    WxErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
