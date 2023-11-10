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
public class BaseException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = -3072387467123480804L;

    private Integer code;
    private String message;

    private BaseException(String message) {
        super(message);
    }

    private BaseException(int code, String message) {
        super(message);
        this.code = code;
    }

    public static BaseException show(ErrorCode errorCode, String message){
        return new BaseException(errorCode.getId(), message);
    }

    public static BaseException show(String message){
        String msg = String.join(":", ErrorCode.PACKET_FORMAT_ERROR.getDesc(), message);
        return new BaseException(ErrorCode.PACKET_FORMAT_ERROR.getId(), msg);
    }

}
