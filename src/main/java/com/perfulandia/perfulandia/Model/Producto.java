package com.perfulandia.perfulandia.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "productos")
@Data
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nombre;
    
    @Column
    private String descripcion;
    
    @Column(nullable = false)
    private Double precio;
    
    @Column
    private String categoria;
    
    @Column
    private String imagen;
    
    @Column
    private Integer stock;
}