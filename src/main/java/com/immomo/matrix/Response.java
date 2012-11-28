package com.immomo.matrix;

import java.io.Serializable;

/**
 * @author mixueqiang
 * @since 2012-10-18
 * 
 */
public class Response implements Serializable {
    private static final long serialVersionUID = 1L;

    private final long id;
    private String errorCode;
    private String message;
    private Object payload;

    public Response(long id) {
        this.id = id;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public Object getPayload() {
        return payload;
    }

    public boolean isError() {
        return errorCode != null;
    }

    public void setErrorAndMessage(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "Response [id=" + id + ", payload=" + payload + ", errorCode=" + errorCode + ", message=" + message
                + "]";
    }

}
