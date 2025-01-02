package com.assignment.vehiclemanagementsystem.payload.respone;

public class ResponseError extends  ResponseData<Object> {
    public ResponseError(int status, String message) {
        super(status, message);
    }
}
