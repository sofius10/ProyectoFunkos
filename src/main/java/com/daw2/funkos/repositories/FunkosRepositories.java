package com.daw2.funkos.repositories;

import com.daw2.funkos.models.Funko;
import java.math.BigDecimal;
import java.util.*;

public interface FunkosRepositories {
    List<Funko>       buscar(Optional<String> categoria, Optional<String> nombre,
                             Optional<BigDecimal> precioMax);
    Optional<Funko>   findById(Long id);
    boolean           existsByNombre(String nombre);
    Funko             save(Funko funko);
    void              update(Funko funko);
    boolean           deleteById(Long id);
    Map<String, Long> contarPorCategoria();
}