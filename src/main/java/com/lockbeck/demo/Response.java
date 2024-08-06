package com.lockbeck.demo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    public Response(int status, String msg,LocalDateTime timestamp) {
        this.status = status;
        this.msg = msg;
        this.timestamp= timestamp;
    }

    private int status;
    private String msg;
    private LocalDateTime timestamp;
    private Object data;

}
