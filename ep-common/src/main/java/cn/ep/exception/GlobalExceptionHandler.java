package cn.ep.exception;

import cn.ep.enums.GlobalEnum;
import cn.ep.utils.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author deschen
 * @Create 2019/9/16
 * @Description
 * @Since 1.0.0
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

//    @ExceptionHandler(value = Throwable.class)
//    public String exception() {
//        return "统一异常处理";
//    }

    @ExceptionHandler({Throwable.class})
    public ResultVO handleThrowable(HttpServletRequest request, Throwable e) {
        log.error("错误请求url = {}", request.getRequestURI(), e);
        return ResultVO.failure(GlobalEnum.SERVICE_ERROR)
                .addExtra("stackTrace", e.getStackTrace())
                .addExtra("exceptionMessage", e.getClass().getName() + ": " + e.getMessage());
    }

    /**
     * 自定义异常
     */
    /**
     * 服务异常
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler({GlobalException.class})
    public ResultVO handleGlobalException(HttpServletRequest request, GlobalException e) {
        log.error("execute methond exception error.url is {}", request.getRequestURI(), e);
        return ResultVO.failure(e.getCode(), e.getMessage())
                .addExtra("stackTrace", e.getStackTrace())
                .addExtra("exceptionMessage", e.getClass().getName() + ": " + e.getMessage());
    }
}
