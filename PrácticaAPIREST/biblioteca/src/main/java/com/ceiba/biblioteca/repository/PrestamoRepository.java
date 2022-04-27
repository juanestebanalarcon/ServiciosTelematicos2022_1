package com.ceiba.biblioteca.repository;

import com.ceiba.biblioteca.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
}