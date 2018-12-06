package com.zxsoft.example.model.http;

import java.io.Serializable;

/**
 * @author chenyx
 * @date create 2017/7/24
 * @description
 */
public class SmartResponse<T> implements Serializable {


    public int status;
    public String msg;
    public T data;

    @Override
    public String toString() {
        return "SmartResponse{\n" +//
                "\tstatus=" + status + "\n" +//
                "\tmsg='" + msg + "\'\n" +//
                "\tdata=" + data + "\n" +//
                '}';
    }
}
