package com.ceiba.biblioteca;
import com.ceiba.biblioteca.entity.Prestamo;
import com.ceiba.biblioteca.service.IPrestamoService;
import com.ceiba.biblioteca.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/prestamo")
public class PrestamoControlador {
    public  LocalDate addDaysOmitiendoFinDeSemana(LocalDate fecha, int dias) {
        LocalDate resultado = fecha;
        int diasAgregados = 0;
        while (diasAgregados < dias) {
            resultado = resultado.plusDays(1);
            if (!(resultado.getDayOfWeek() == DayOfWeek.SATURDAY || resultado.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                ++diasAgregados;
            }
        }
        return resultado;
    }
    @Autowired
    IPrestamoService prestamoService;

    @GetMapping("/{Id}")
    public ResponseEntity<Prestamo> read(@PathVariable Long Id) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Map<String,Object>jsonResponse = new HashMap<String,Object>();
        Optional<Prestamo>result = prestamoService.buscarPorId(Id);
        jsonResponse.put("id",result.get().getId());
        jsonResponse.put("fechaMaximaDevolucion",result.get().getFechaMaximaDevolucion().format(formato));
        jsonResponse.put("isbn",result.get().getIsbn());
        jsonResponse.put("identificacionUsuario",result.get().getIdentificacionUsuario());
        jsonResponse.put("tipoUsuario",result.get().getTipoUsuario());
        return new ResponseEntity(jsonResponse,HttpStatus.OK);
    }

@PutMapping("/{Id}")
public ResponseEntity<Prestamo>Delete(@PathVariable Long Id, @RequestBody Prestamo prestamo){
    Map<String,Object>jsonResponse = new HashMap<String,Object>();
    Optional<Prestamo>result = prestamoService.buscarPorId(Id);
    if(result.isEmpty()){
        jsonResponse.put("Error","Id not found");
        return new ResponseEntity(jsonResponse,HttpStatus.NOT_FOUND);
    }
    Prestamo prestamoExiste = prestamoService.buscarPorId(Id).get();
    prestamoExiste.setIsbn(prestamo.getIsbn());
    prestamoExiste.setIdentificacionUsuario(prestamo.getIdentificacionUsuario());
    prestamoExiste.setTipoUsuario(prestamo.getTipoUsuario());
    prestamoService.actualizar(prestamoExiste);
    jsonResponse.put("id",prestamoExiste.getId());
    jsonResponse.put("isbn",prestamoExiste.getIsbn());
    jsonResponse.put("identificacionUsuario",prestamoExiste.getIdentificacionUsuario());
    jsonResponse.put("tipoUsuario",prestamoExiste.getTipoUsuario());
    return new ResponseEntity(jsonResponse,HttpStatus.OK);
}

@DeleteMapping("/{Id}")
public ResponseEntity<Prestamo>Delete(@PathVariable Long Id){
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    Map<String,Object>jsonResponse = new HashMap<String,Object>();
    Optional<Prestamo>result = prestamoService.buscarPorId(Id);
    if(result.isEmpty()){
        jsonResponse.put("Error","Id not found");
        return new ResponseEntity(jsonResponse,HttpStatus.NOT_FOUND);
    }
    prestamoService.eliminar(Id);
    jsonResponse.put("Message","Record successfully deleted!");
    return new ResponseEntity(jsonResponse,HttpStatus.OK);
}
    @PostMapping
    public ResponseEntity<Prestamo> prestarLibro(@RequestBody Prestamo prestamo) {
        LocalDate fechaPrestamo = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if (prestamo.getTipoUsuario() ==3) {
            if(!prestamoService.findByidentificacionUsuario(prestamo.getIdentificacionUsuario()).isEmpty()) {
                Map<String,Object>jsonResponse = new HashMap<String,Object>();
                jsonResponse.put("mensaje",String.format("El usuario con identificación %s ya tiene un libro prestado por lo cual no se le puede realizar otro préstamo", prestamo.getIdentificacionUsuario()));
                return new ResponseEntity(jsonResponse,HttpStatus.BAD_REQUEST);
            }else {
                fechaPrestamo = addDaysOmitiendoFinDeSemana(fechaPrestamo, 7);
                fechaPrestamo.format(formato);
                prestamo.setFechaMaximaDevolucion(fechaPrestamo);
                prestamoService.prestarLibro(prestamo);
                Map<String,Object>jsonResponse = new HashMap<String,Object>();
                jsonResponse.put("id",prestamo.getId());
                jsonResponse.put("fechaMaximaDevolucion",fechaPrestamo.format(formato));
                return new ResponseEntity(jsonResponse,HttpStatus.OK);
            }
        }else if(prestamo.getTipoUsuario() == 1) {
            fechaPrestamo = addDaysOmitiendoFinDeSemana(fechaPrestamo, 10);
            prestamo.setFechaMaximaDevolucion(fechaPrestamo);
            prestamoService.prestarLibro(prestamo);
            Map<String,Object>jsonResponse = new HashMap<String,Object>();
            jsonResponse.put("id",prestamo.getId());
            jsonResponse.put("fechaMaximaDevolucion",fechaPrestamo.format(formato));
            return new ResponseEntity(jsonResponse ,HttpStatus.OK);
        }else if(prestamo.getTipoUsuario() == 2) {
            Map<String,Object>jsonResponse = new HashMap<String,Object>();
            fechaPrestamo = addDaysOmitiendoFinDeSemana(fechaPrestamo, 8);
            fechaPrestamo.format(formato);
            prestamo.setFechaMaximaDevolucion(fechaPrestamo);
            prestamoService.prestarLibro(prestamo);
            jsonResponse.put("id",prestamo.getId());
            jsonResponse.put("fechaMaximaDevolucion",fechaPrestamo.format(formato));
            return new ResponseEntity(jsonResponse,HttpStatus.OK);
        }
        else {
            Map<String,Object>jsonResponse = new HashMap<String,Object>();
            jsonResponse.put("mensaje","Tipo de usuario no permitido en la biblioteca");
            return new ResponseEntity(jsonResponse,HttpStatus.BAD_REQUEST);
        }
    }
}