package com.immomo.matrix;

import java.io.Serializable;

/**
 * @author mixueqiang
 * @since 2012-10-18
 * 
 */
public class Response implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private boolean isError = false;
    private String message;
    private Object payload;

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
        return isError;
    }

    public void setError(boolean isError) {
        this.isError = isError;
    }

    public void setErrorAndMessage(String message) {
        this.isError = true;
        this.message = message;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "Response [id=" + id + ", isError=" + isError + ", payload=" + payload + "]";
    }

}
