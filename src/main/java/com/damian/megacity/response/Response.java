package com.damian.megacity.response;

import java.io.Serializable;

public class Response implements Serializable {
    private int status;
    private String message;
    private Object data;

    public Response setStatus(int status) {
        this.status = status;
        return this;
    }

    public Response setMessage(String message) {
        this.message = message;
        return this;
    }

    public Response setData(Object data) {
        this.data = data;
        return this;
    }

    public Response build() {
        return this;
    }
}
