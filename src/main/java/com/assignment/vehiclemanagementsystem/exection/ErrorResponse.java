package com.assignment.vehiclemanagementsystem.exection;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorResponse {
    private Date timestamp;
    private int status;
    private String path;
    private String error;
    private String message;

}
