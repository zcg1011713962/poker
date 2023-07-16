package org.cloud.intercept;


import cn.hutool.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;
import org.cloud.entity.common.BaseResponse;
import org.cloud.entity.exception.FutureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(value = Exception.class)
    public BaseResponse handleMyException(Exception ex) {
        log.error("{}", ex);
        if(ex instanceof FutureException){
            FutureException e = (FutureException) ex;
            return BaseResponse.failed(e.getCode(), e.getMessage());
        }
        return BaseResponse.failed(HttpStatus.HTTP_INTERNAL_ERROR, ex.getMessage());
    }

}
