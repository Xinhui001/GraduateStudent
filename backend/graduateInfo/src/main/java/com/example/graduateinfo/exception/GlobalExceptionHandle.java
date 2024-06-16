package com.example.graduateinfo.exception;


import com.example.graduateinfo.common.BaseResponse;
import com.example.graduateinfo.common.ErrorCode;
import com.example.graduateinfo.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author 20891
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandle {
    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceptionHandle(BusinessException e) {
        log.error("businessException", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, e.getMessage(), "");
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionHandle(BusinessException e) {
        log.error("runtimeException", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, e.getMessage(), "");
    }
}
