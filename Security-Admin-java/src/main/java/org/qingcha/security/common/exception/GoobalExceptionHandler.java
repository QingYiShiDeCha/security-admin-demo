package org.qingcha.security.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.qingcha.security.common.result.AjaxResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GoobalExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    public AjaxResult handler(RuntimeException e) {
        log.error("运行时异常： {}", e.getMessage());
        return AjaxResult.error(e.getMessage());
    }
}
