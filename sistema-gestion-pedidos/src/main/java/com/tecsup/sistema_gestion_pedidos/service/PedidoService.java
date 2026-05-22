package com.tecsup.sistema_gestion_pedidos.service;

import com.tecsup.sistema_gestion_pedidos.dto.PedidoRequestDTO;
import com.tecsup.sistema_gestion_pedidos.dto.DetallePedidoDTO;
import com.tecsup.sistema_gestion_pedidos.model.*;
import com.tecsup.sistema_gestion_pedidos.repository.*;
import com.tecsup.sistema_gestion_pedidos.exception.ResourceNotFoundException;
import com.tecsup.sistema_gestion_pedidos.exception.StockInsuficienteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public Pedido findById(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con ID: " + id));
    }

    @Transactional
    public Pedido registrarPedido(PedidoRequestDTO request) {
        // 1. Buscar cliente
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + request.getClienteId()));

        // 2. Crear pedido
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setFecha(LocalDate.now());
        pedido.setTotal(0.0);
        pedido = pedidoRepository.save(pedido);

        double totalPedido = 0.0;

        // 3. Procesar cada detalle
        for (DetallePedidoDTO dto : request.getDetalles()) {
            Producto producto = productoRepository.findById(dto.getProductoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + dto.getProductoId()));

            // Validar stock suficiente
            if (producto.getStock() < dto.getCantidad()) {
                throw new StockInsuficienteException("Stock insuficiente para el producto: " + producto.getNombre() +
                        ". Stock disponible: " + producto.getStock());
            }

            // Calcular subtotal (cantidad x precio)
            double subtotal = producto.getPrecio() * dto.getCantidad();
            totalPedido += subtotal;

            // Crear detalle
            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedido);
            detalle.setProducto(producto);
            detalle.setCantidad(dto.getCantidad());
            detalle.setSubtotal(subtotal);
            detallePedidoRepository.save(detalle);

            // ACTUALIZAR STOCK (requisito clave)
            producto.setStock(producto.getStock() - dto.getCantidad());
            productoRepository.save(producto);
        }

        // 4. Actualizar el total del pedido
        pedido.setTotal(totalPedido);
        return pedidoRepository.save(pedido);
    }

    public void deleteById(Long id) {
        Pedido pedido = findById(id);
        pedidoRepository.deleteById(id);
    }
}