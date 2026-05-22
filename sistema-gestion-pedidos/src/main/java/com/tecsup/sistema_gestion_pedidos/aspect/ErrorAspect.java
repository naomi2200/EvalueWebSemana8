package com.tecsup.sistema_gestion_pedidos.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ErrorAspect {

    @AfterThrowing(pointcut = "execution(* com.tecsup.sistema_gestion_pedidos.service.*.*(..))", throwing = "error")
    public void logError(JoinPoint joinPoint, Throwable error) {
        System.out.println("========================================");
        System.out.println("ERROR capturado en el método: " + joinPoint.getSignature().getName());
        System.out.println("Mensaje de error: " + error.getMessage());
        System.out.println("========================================");
    }
}