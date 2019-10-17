package cn.ep.annotation;

import cn.ep.courseenum.RoleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CanDelete {
    RoleEnum[] role() default RoleEnum.ADMIN;
}
