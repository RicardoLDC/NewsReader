package com.zxsoft.example.model.http;

import java.io.Serializable;

public class OssResponse<T> implements Serializable {

    public int error_code;
    public String reason;
    public T result;

    @Override
    public String toString() {
        return "OssResponse{" +
                "error_code=" + error_code +
                ", reason='" + reason + '\'' +
                ", result=" + result +
                '}';
    }
}
