package com.ceiba.biblioteca.service;

import com.ceiba.biblioteca.entity.Prestamo;
import java.util.*;

public interface IPrestamoService {
    public List<Prestamo> listarTodos();
    public Optional<Prestamo> buscarPorId(Long id);
    public List<Prestamo>findByidentificacionUsuario(String identificacionUsuario);
    public Prestamo prestarLibro(Prestamo prestamo);
    public void eliminar(Long id);

}
