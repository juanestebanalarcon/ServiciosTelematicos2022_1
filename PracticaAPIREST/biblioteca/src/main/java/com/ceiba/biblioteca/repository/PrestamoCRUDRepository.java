package com.ceiba.biblioteca.repository;

import com.ceiba.biblioteca.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PrestamoCRUDRepository extends CrudRepository<Prestamo, Long> {
List<Prestamo>findByIdentificacionUsuario(String IdentificacionUsuario);
}