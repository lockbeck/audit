package com.lockbeck.demo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    public Response(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private int status;
    private String msg;
    private Object data;

}
