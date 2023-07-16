package org.cloud.entity.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
@Slf4j
@Data
@AllArgsConstructor
@ToString
public class FutureException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = -3072387467123480803L;
    private Integer code;
    private String message;

    public FutureException(String message) {
        super(message);
    }

    public FutureException(int code, String message) {
        super(message);
        this.code = code;
    }

}
