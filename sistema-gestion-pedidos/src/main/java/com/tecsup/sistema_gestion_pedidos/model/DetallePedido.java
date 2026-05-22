package com.tecsup.sistema_gestion_pedidos.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "detalle_pedido")
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cantidad;

    private Double subtotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "detalles"})
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "categoria"})
    private Producto producto;

    public DetallePedido() {}

    // Getters
    public Long getId() { return id; }
    public Integer getCantidad() { return cantidad; }
    public Double getSubtotal() { return subtotal; }
    public Pedido getPedido() { return pedido; }
    public Producto getProducto() { return producto; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }
    public void setProducto(Producto producto) { this.producto = producto; }
}