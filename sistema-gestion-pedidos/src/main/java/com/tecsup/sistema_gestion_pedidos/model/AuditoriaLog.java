package com.tecsup.sistema_gestion_pedidos.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "auditoria_log")
public class AuditoriaLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accion;

    private String metodo;

    private LocalDateTime fecha;

    private String usuario;

    // Constructor vacio
    public AuditoriaLog() {}

    // Getters
    public Long getId() { return id; }
    public String getAccion() { return accion; }
    public String getMetodo() { return metodo; }
    public LocalDateTime getFecha() { return fecha; }
    public String getUsuario() { return usuario; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setAccion(String accion) { this.accion = accion; }
    public void setMetodo(String metodo) { this.metodo = metodo; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
}