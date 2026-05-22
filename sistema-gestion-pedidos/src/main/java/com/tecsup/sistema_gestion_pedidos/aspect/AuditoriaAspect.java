package com.tecsup.sistema_gestion_pedidos.aspect;

import com.tecsup.sistema_gestion_pedidos.model.AuditoriaLog;
import com.tecsup.sistema_gestion_pedidos.repository.AuditoriaLogRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Aspect
@Component
public class AuditoriaAspect {

    @Autowired
    private AuditoriaLogRepository auditoriaLogRepository;

    @Before("execution(* com.tecsup.sistema_gestion_pedidos.controller.*.*(..))")
    public void registrarAuditoria(JoinPoint joinPoint) {
        // Obtener el usuario autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String usuario = (auth != null) ? auth.getName() : "anonymous";

        // Crear registro de auditoría
        AuditoriaLog log = new AuditoriaLog();
        log.setAccion("EJECUCION");
        log.setMetodo(joinPoint.getSignature().getName());
        log.setFecha(LocalDateTime.now());
        log.setUsuario(usuario);

        // Guardar en la base de datos
        auditoriaLogRepository.save(log);

        System.out.println("[AUDITORIA] Usuario: " + usuario + " ejecutó: " + joinPoint.getSignature().getName());
    }
}