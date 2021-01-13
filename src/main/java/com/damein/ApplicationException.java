package com.damein;

public class ApplicationException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    /**
     * 错误代码
     */
    private int code;

    public ApplicationException() {
    }

    /**
     * 构造函数
     *
     * @param code    错误代码
     * @param message 错误描述
     */
    public ApplicationException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 构造函数
     *
     * @param code    错误代码
     * @param message 错误描述
     * @param cause   错误对象
     */
    public ApplicationException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
