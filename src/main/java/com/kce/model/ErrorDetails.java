package com.kce.modal;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDetails {
    private Date timestamp;
    private String statusCode;
    private String description;

    public ErrorDetails(Date timestamp, String statusCode, String description) {
        this.timestamp=timestamp;
        this.statusCode=statusCode;
        this.description=description;
    }
}
