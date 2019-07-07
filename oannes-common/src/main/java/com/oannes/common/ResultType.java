package com.oannes.common;

public enum ResultType {
    OK(100),FAIL(500);
    int code;
    ResultType(int code){
        this.code=code;
    }


}
