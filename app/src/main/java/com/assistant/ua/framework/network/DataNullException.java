package com.assistant.ua.framework.network;

/**
 * Created by qinbaoyuan on 2018/11/19
 */
public class DataNullException extends Exception {
    private int code = 0x10010;

    public DataNullException(String msg) {
        super(msg);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}