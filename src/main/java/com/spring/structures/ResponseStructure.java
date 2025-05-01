package com.spring.structures;

public class ResponseStructure<T> {
    private String msg;
    private int statusCode;
    private T data;

    public ResponseStructure() {}

    public ResponseStructure(String msg, int statusCode, T data) {
        this.msg = msg;
        this.statusCode = statusCode;
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
