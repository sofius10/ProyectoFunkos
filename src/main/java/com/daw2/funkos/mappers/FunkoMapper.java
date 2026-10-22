package com.daw2.funkos.mappers;


import com.daw2.funkos.dto.*;
import com.daw2.funkos.models.Funko;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class FunkoMapper {

    public FunkoResponse toResponse(Funko f) {
        return new FunkoResponse(f.id(), f.nombre(), f.precio(), f.cantidad(),
                f.categoria(), f.createdAt(), f.updatedAt());
    }

    public List<FunkoResponse> toResponse(List<Funko> funkos) {
        return funkos.stream().map(this::toResponse).toList();
    }

    /** POST: nuevo. El id lo pone la BD; las fechas, el servidor. */
    public Funko toModel(FunkoCreateRequest r) {
        var ahora = LocalDateTime.now();
        return new Funko(
                null,
                r.nombre().trim(),
                r.precio(),
                r.cantidad(),
                r.categoria().toUpperCase(),
                ahora,
                ahora);
    }

    /** PUT: reemplaza todo menos el id y la fecha de creación. */
    public Funko reemplazar(Funko original, FunkoCreateRequest r) {
        return new Funko(
                original.id(),
                r.nombre().trim(),
                r.precio(),
                r.cantidad(),
                r.categoria().toUpperCase(),
                original.createdAt(),
                LocalDateTime.now());
    }

    /** PATCH: cambia solo lo que llega distinto de null. */
    public Funko parchear(Funko original, FunkoUpdateRequest r) {
        return new Funko(
                original.id(),
                r.nombre()    != null ? r.nombre().trim()           : original.nombre(),
                r.precio()    != null ? r.precio()                  : original.precio(),
                r.cantidad()  != null ? r.cantidad()                : original.cantidad(),
                r.categoria() != null ? r.categoria().toUpperCase() : original.categoria(),
                original.createdAt(),
                LocalDateTime.now());
    }
}