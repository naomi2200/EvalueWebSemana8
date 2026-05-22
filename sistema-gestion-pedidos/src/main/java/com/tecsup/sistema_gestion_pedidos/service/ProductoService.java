package com.tecsup.sistema_gestion_pedidos.service;

import com.tecsup.sistema_gestion_pedidos.model.Producto;
import com.tecsup.sistema_gestion_pedidos.model.Categoria;
import com.tecsup.sistema_gestion_pedidos.repository.ProductoRepository;
import com.tecsup.sistema_gestion_pedidos.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public Producto findById(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
    }

    public Producto save(Producto producto) {
        if (producto.getCategoria() != null && producto.getCategoria().getId() != null) {
            Categoria categoria = categoriaRepository.findById(producto.getCategoria().getId())
                    .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
            producto.setCategoria(categoria);
        }
        return productoRepository.save(producto);
    }

    public Producto update(Long id, Producto productoDetails) {
        Producto producto = findById(id);
        producto.setNombre(productoDetails.getNombre());
        producto.setDescripcion(productoDetails.getDescripcion());
        producto.setPrecio(productoDetails.getPrecio());
        producto.setStock(productoDetails.getStock());

        if (productoDetails.getCategoria() != null && productoDetails.getCategoria().getId() != null) {
            Categoria categoria = categoriaRepository.findById(productoDetails.getCategoria().getId())
                    .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
            producto.setCategoria(categoria);
        }
        return productoRepository.save(producto);
    }

    public void deleteById(Long id) {
        productoRepository.deleteById(id);
    }
}