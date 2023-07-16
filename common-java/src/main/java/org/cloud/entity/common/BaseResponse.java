package org.cloud.entity.common;

import cn.hutool.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> implements Serializable {
    private static final long serialVersionUID = 9088417783244165451L;
    private int status;
    private String message;
    private T data;
    private static BaseResponse success = new BaseResponse(HttpStatus.HTTP_OK, "success" , null);
    private static BaseResponse exception = new BaseResponse(HttpStatus.HTTP_INTERNAL_ERROR, null , null);

    public static BaseResponse success(Object data) {
        success.setData(data);
        return success;
    }

    public static BaseResponse exception(Object data) {
        success.setData(data);
        return exception;
    }

    public static BaseResponse failed(int status, Object data) {
        return new BaseResponse<>(status, null, data);
    }

}
