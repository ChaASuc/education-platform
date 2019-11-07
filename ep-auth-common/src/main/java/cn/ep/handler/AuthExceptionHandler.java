//package cn.ep.handler;
//
//import cn.ep.enums.GlobalEnum;
//import cn.ep.exception.GlobalException;
//import cn.ep.exception.GlobalExceptionHandler;
//import cn.ep.utils.ResultVO;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.validation.BindException;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.ConstraintViolation;
//import javax.validation.ConstraintViolationException;
//import java.util.Set;
//import java.util.stream.Collectors;
//
///**
// * @Author deschen
// * @Create 2019/9/16
// * @Description
// * @Since 1.0.0
// */
//@RestControllerAdvice
// @Slf4j
//public class AuthExceptionHandler extends GlobalExceptionHandler {
//
//
//    /**
//     * 服务异常
//     *
//     * @param request
//     * @param e
//     * @return
//     */
//    @ExceptionHandler({AccessDeniedException.class})
//    public ResultVO handleAccessDeniedException(HttpServletRequest request, AccessDeniedException e) {
//        log.error("错误请求url = {}", request.getRequestURI(), e);
//        return ResultVO.failure(GlobalEnum.NO_AUTHORIZATION.getCode(), e.getMessage())
//                .addExtra("stackTrace", e.getStackTrace())
//                .addExtra("exceptionMessage", e.getClass().getName() + ": " + e.getMessage());
//    }
//
//}
