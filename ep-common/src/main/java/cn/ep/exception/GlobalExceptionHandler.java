package cn.ep.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author deschen
 * @Create 2019/9/16
 * @Description
 * @Since 1.0.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Throwable.class)
    public String exception() {
        return "统一异常处理";
    }

}
