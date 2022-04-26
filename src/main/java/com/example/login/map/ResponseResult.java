package com.example.login.map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class ResponseResult {
    private String error;
    private Object data;
    private int status;

    public ResponseResult(HttpStatus status, String error, Object data) {
        this.error = error;
        this.data = data;
        this.status = status.value();
    }

    public ResponseResult(HttpStatus status, String error) {
        this.status = status.value();
        this.error = error;
    }

    public ResponseResult(HttpStatus status, Object data) {

    }

}
