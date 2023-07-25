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

    public static BaseResponse success(Object data) {
        return new BaseResponse(HttpStatus.HTTP_OK, "成功" , data);
    }

    public static BaseResponse exception(Object data) {
        return new BaseResponse(HttpStatus.HTTP_INTERNAL_ERROR, "异常" , data);
    }

    public static BaseResponse failed(int status, Object data) {
        return new BaseResponse<>(status, "失败", data);
    }

}
