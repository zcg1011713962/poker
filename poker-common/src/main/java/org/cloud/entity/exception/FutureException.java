package org.cloud.entity.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.cloud.enums.ErrorCode;

import java.io.Serializable;
@Slf4j
@Data
@AllArgsConstructor
@ToString
public class FutureException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = -3072387467123480803L;
    private Integer code;
    private String message;

    private FutureException(String message) {
        super(message);
    }

    private FutureException(int code, String message) {
        super(message);
        this.code = code;
    }

    public static FutureException show(int code, String message){
        return FutureException.show(code, message);
    }

    public static FutureException show(String message){
        String msg = String.join(":", ErrorCode.FUTURE_ERROR.getDesc(), message);
        return FutureException.show(ErrorCode.FUTURE_ERROR.getId(), msg);
    }

}
