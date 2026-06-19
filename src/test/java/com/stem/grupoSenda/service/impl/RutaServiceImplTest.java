package com.stem.grupoSenda.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import com.stem.grupoSenda.model.Ruta;
import com.stem.grupoSenda.repository.RutaRepository;
import com.stem.grupoSenda.service.RutaService;

@SpringBootTest
@Transactional
public class RutaServiceImplTest {

    @Autowired
    private RutaService rutaService;

    @Autowired
    private RutaRepository rutaRepository;

    

    @Test
    void testCrearRuta() {
        Ruta nuevaRuta = new Ruta("Senderismo Río", "Ruta suave", 100, "Fácil", 300, 5.5);
        Ruta resultado = rutaService.crearRuta(nuevaRuta);
        assertNotNull(resultado.getId());
    }

    @Test
    void testListarRuta() {
        rutaRepository.save(new Ruta("Ruta 1", "Desc", 50, "Baja", 100, 2.0));
        List<Ruta> rutas = rutaService.listarRuta();
        assertTrue(rutas.size() >= 1);
    }

    @Test
    void testActualizarRuta_SoloParcial() {
        Ruta original = rutaRepository.save(new Ruta("NombreOriginal", "DescOriginal", 100, "Fácil", 200, 5.0));
        Ruta cambios = new Ruta();
        cambios.setNombre("NombreNuevo");
        
        Optional<Ruta> resultado = rutaService.actualizarRuta(original.getId(), cambios);
        
        assertTrue(resultado.isPresent());
        assertEquals("NombreNuevo", resultado.get().getNombre());
        assertEquals("DescOriginal", resultado.get().getDescripcion());
    }

    @Test
    void testObtenerRutaPorId_NoExistente() {
        Optional<Ruta> resultado = rutaService.obtenerRutaPorId(999L);
        assertFalse(resultado.isPresent());
    }
}