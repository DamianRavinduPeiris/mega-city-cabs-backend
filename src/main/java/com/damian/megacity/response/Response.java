package com.damian.megacity.response;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;

@Builder
@AllArgsConstructor
public class Response implements Serializable {
    private int status;
    private String message;
    private Object data;

}
