package cn.ep.aspect;

import cn.ep.annotation.CanAdd;
import cn.ep.annotation.CanLook;
import cn.ep.courseenum.RoleEnum;
import cn.ep.enums.GlobalEnum;
import cn.ep.exception.GlobalException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


// 控制多个Aspect的执行顺序，越小越先执行，
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
@Order(-98)
@Component
public class OperationHelper {

    @Pointcut(value = "@annotation(cn.ep.annotation.CanAdd)")
    void addPointCut(){}
    @Pointcut(value = "@annotation(cn.ep.annotation.CanDelete)")
    void deletePointCut(){}
    @Pointcut(value = "@annotation(cn.ep.annotation.CanLook)")
    void lookPointCut(){}
    @Pointcut(value = "@annotation(cn.ep.annotation.CanModify)")
    void modifyPointCut(){}


    //todo 待权限模块实现后，再行修改，不热部署，需重启，不然会报错


    @Before(value = "@annotation(canAdd)", argNames = "canAdd")
    //@Order(1)
    void addAdvice(CanAdd canAdd){

        System.out.println(canAdd.role().length);
        boolean canAdd1 = true;
        //....

        if (!canAdd1){
            throw new GlobalException(GlobalEnum.OPERATION_ERROR,"你没有增加的权限");
        }
        System.out.println("add");
    }

    @Before(value = "deletePointCut()")
    //@Order(2)
    void deleteAdvice(JoinPoint joinPoint){

        boolean canDelete = true;
        if (!canDelete){
            throw new GlobalException(GlobalEnum.OPERATION_ERROR,"你没有删除的权限");
        }
        System.out.println("Delete");
    }

    @Before(value = "lookPointCut()")
    //@Order(4)
    void lookAdvice(JoinPoint joinPoint){
        CanLook look = ((MethodSignature)joinPoint.getSignature())
                .getMethod().getAnnotation(CanLook.class);
        System.out.println(look.role().length);
       // RoleEnum[] roleEnum = look.role();
        //System.out.println(roleEnum);

        boolean canLook = true;
        if (!canLook){
            throw new GlobalException(GlobalEnum.OPERATION_ERROR,"你没有查看的权限");
        }
        System.out.println("look");
    }


    @Before(value = "modifyPointCut()")
   // @Order(3)
    void modifyAdvice(JoinPoint joinPoint){

        boolean canModify = true;
        if (!canModify){
            throw new GlobalException(GlobalEnum.OPERATION_ERROR,"你没有修改的权限");
        }
        System.out.println("modify");
    }


}
