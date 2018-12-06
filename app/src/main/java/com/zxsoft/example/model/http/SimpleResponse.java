package com.zxsoft.example.model.http;

import java.io.Serializable;

/**
 * @author chenyx
 * @date create 2017/7/24
 * @description
 */
public class SimpleResponse implements Serializable {


    public int code;
    public String msg;

    public SmartResponse toResponse() {
        SmartResponse lzyResponse = new SmartResponse();
        lzyResponse.status = code;
        lzyResponse.msg = msg;
        return lzyResponse;
    }
}
