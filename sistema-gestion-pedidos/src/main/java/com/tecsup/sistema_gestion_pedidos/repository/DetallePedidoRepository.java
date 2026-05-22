package com.tecsup.sistema_gestion_pedidos.repository;

import com.tecsup.sistema_gestion_pedidos.model.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {
}