package com.kce.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Setter
@Getter
@Document(collection = "requests")
public class Request {

    @Id
    @JsonProperty("id")
    private String id;

    private String name;
    private String registerNumber;
    private String department;
    private String type;
    private String location;
    private String priority;
    private String subject;
    private String description;
    private String status;
    private String responseMessage;
    private LocalDateTime createdAt;

    // Add Getter & Setter for status
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Add Getter & Setter for responseMessage if used
    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    // Other getters and setters...
}
