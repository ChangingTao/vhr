package org.javaboy.vhr;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName AopTest
 * @Author 周某
 * @Date 2020-05-13 9:00
 **/
@Component
@Aspect
public class AopTest {

    /*@Before("execution(* org.javaboy.vhr.controller.sys.basic.*.*(..))")
    public void beforeTest(JoinPoint joinPoint) {
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        Signature signature = joinPoint.getSignature();
        System.out.println("signature.toString() ==> " + signature.toString());
        System.out.println("signature.toShortString() ==> " + signature.toShortString());
        System.out.println("signature.toLongString() ==> " + signature.toLongString());
        System.out.println("signature.getName() ==> " + signature.getName());
        System.out.println("signature.getModifiers() ==> " + signature.getModifiers());
        System.out.println("signature.getDeclaringType() ==> " + signature.getDeclaringType());
        System.out.println("signature.getDeclaringTypeName() ==> " + signature.getDeclaringTypeName());
    }*/
}
