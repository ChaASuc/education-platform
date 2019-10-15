package cn.ep.aspect;

import cn.ep.enums.GlobalEnum;
import cn.ep.exception.GlobalException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


// order注解 控制多个Aspect的执行顺序，越小越先执行，
// aop是个同心圆，beforeA-》beforeB-》method-》afterB-》afterA
// 最外层的order值最小

/**
 如果在同一接入点（join point) 有多个通知（advice），Spring AOP 采用和 AspectJ
 类似的优先级来指定通知的执行顺序，目标执行前（进入时），优先级高的通知先执行，
 目标执行后（出来时），优先级高的通知后执行。

 如果两个通知分别定义在各自的 Aspect 内，可以通过如下两种方式控制 Aspect 的施加顺序：
 * Aspect 类添加注解：org.springframework.core.annotation.Order
 顺序值：使用注解属性指定
 * Aspect 类实现接口：org.springframework.core.Ordered
 顺序值：实现 Ordered 接口的 getOrder() 方法即可
 如果两个 advice 位于同一 aspect 内，且执行顺序有先后，通过 advice 的声明顺序是无法确定
 其执行顺序的，因为 advice 方法的声明顺序无法通过反射获取，只能采取如下变通方式，二选一：
 * 将两个 advice 合并为一个 advice，那么执行顺序就可以通过代码控制了
 * 将两个 advice 分别抽离到各自的 aspect 内，然后为 aspect 指定执行顺序
 控制多个Aspect的执行顺序，越小越先执行*/
@Aspect
@Order(-99)
@Component
public class LoginHelper {

    @Pointcut(value = "@annotation(cn.ep.annotation.IsLogin)")
    void myPointcut(){}


    //或者使用环绕通知
    @Before("myPointcut()")
    //配合全局异常处理
    void isLogin(JoinPoint joinPoint) throws GlobalException {
        boolean isLogin = true;

        if (!isLogin){
            throw new GlobalException(GlobalEnum.OPERATION_ERROR,"你还没有登录");
        } else {
            System.out.println("已经登陆");
        }
    }
}
