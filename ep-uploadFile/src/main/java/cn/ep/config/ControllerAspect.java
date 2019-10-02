//package cn.ep.config;
//
//import com.alibaba.fastjson.JSON;
//import org.apache.commons.lang3.StringUtils;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.Signature;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
///**
// * @Author: wangying
// * @Description:
// * @Date: Created in  2019/1/25
// */
//
//@Aspect
//@Component
//public class ControllerAspect {
//
//    private static final Logger LOG = LoggerFactory.getLogger(ControllerAspect.class);
//
//    @Pointcut("execution(* com.leyou.upload.web..*.*(..))")
//    public void pointCut() {
//    }
//
//    @Around("pointCut()")
//    public Object around(ProceedingJoinPoint pjp) throws Throwable {
//        Signature sig = pjp.getSignature();
//        String name = StringUtils.join(pjp.getTarget().getClass().getName(), ".", pjp.getSignature().getName());
//
//        try {
//            try {
//                LOG.info("-----【{}】---- 进入API接口... request: 【{}】", name, JSON.toJSON(pjp.getArgs()));
//                }catch (Exception e) {}
//            Object proceed = pjp.proceed();
//            try {
//                LOG.info("-----【{}】---- 退出API接口... request: 【{}】, response: 【{}】", name, JSON.toJSON(pjp.getArgs()), JSON.toJSON(proceed));
//            }catch (Exception e) {}
//                return proceed;
//        } catch (Throwable e) {
//            throw e;
//        }
//    }
//
//
//
//
//}
