package com.ceiba.biblioteca.service;

import com.ceiba.biblioteca.entity.Prestamo;
import com.ceiba.biblioteca.repository.PrestamoCRUDRepository;
import com.ceiba.biblioteca.repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrestamoService implements IPrestamoService {
    @Autowired
    PrestamoRepository repository;
    @Autowired
    PrestamoCRUDRepository repositoryCRUD;
    @Override
    public List<Prestamo> listarTodos() {
        return this.repository.findAll();
    }

    @Override
    public Optional<Prestamo> buscarPorId(Long id) {
        return this.repository.findById(id);
    }
    @Override
    public List<Prestamo>findByidentificacionUsuario(String identificacionUsuario) {
        return this.repositoryCRUD.findByIdentificacionUsuario(identificacionUsuario);
    }
    @Override
    public Prestamo prestarLibro(Prestamo prestamo) {
        return this.repository.save(prestamo);
    }

    @Override
    public void eliminar(Long id) {
        this.repository.deleteById(id);
    }
}
