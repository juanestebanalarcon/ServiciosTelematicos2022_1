package com.ceiba.biblioteca.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name="isbn",nullable = false,length = 10)
    private String isbn;
    @Column(name="identificacionUsuario",nullable = false,length = 10)
    private String identificacionUsuario;
    @Column(name="tipoUsuario",nullable = false)
    private int tipoUsuario;
    @Column(name="fechaMaximaDevolucion",nullable = false)
    private LocalDate fechaMaximaDevolucion;

    public Prestamo(String isbn, String identificacionUsuario, int tipoUsuario) {
        this.isbn = isbn;
        this.identificacionUsuario = identificacionUsuario;
        this.tipoUsuario = tipoUsuario;
    }
    public Prestamo(Long id,String isbn, String identificacionUsuario, int tipoUsuario, LocalDate fechaMaximaDevolucion) {
        this.id = id;
        this.isbn = isbn;
        this.identificacionUsuario = identificacionUsuario;
        this.tipoUsuario = tipoUsuario;
        this.fechaMaximaDevolucion = fechaMaximaDevolucion;
    }
    public Prestamo(Long id,LocalDate fechaMaximaDevolucion) {
        this.id = id;
        this.fechaMaximaDevolucion = fechaMaximaDevolucion;
    }
    public Prestamo() {
    }
    //  private TipoUsuario tipo;
}
