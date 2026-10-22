package com.daw2.funkos.repositories;
import com.daw2.funkos.models.Funko;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

@Repository
public class FunkosRepositoryJdbc implements FunkosRepositories {

    private final JdbcClient jdbc;
    private final FunkoRowMapper rowMapper;

    public FunkosRepositoryJdbc(JdbcClient jdbc, FunkoRowMapper rowMapper) {
        this.jdbc = jdbc;
        this.rowMapper = rowMapper;
    }

    @Override
    public List<Funko> buscar(Optional<String> categoria, Optional<String> nombre,
                              Optional<BigDecimal> precioMax) {
        var sql = new StringBuilder("SELECT * FROM funkos WHERE 1 = 1");
        var params = new HashMap<String, Object>();

        categoria.ifPresent(c -> {
            sql.append(" AND UPPER(categoria) = UPPER(:categoria)");
            params.put("categoria", c);
        });
        nombre.ifPresent(n -> {
            sql.append(" AND LOWER(nombre) LIKE LOWER(:nombre)");
            params.put("nombre", "%" + n + "%");
        });
        precioMax.ifPresent(p -> {
            sql.append(" AND precio <= :precioMax");
            params.put("precioMax", p);
        });
        sql.append(" ORDER BY id");

        return jdbc.sql(sql.toString()).params(params).query(rowMapper).list();
    }

    @Override
    public Optional<Funko> findById(Long id) {
        return jdbc.sql("SELECT * FROM funkos WHERE id = :id")
                .param("id", id)
                .query(rowMapper).optional();
    }

    @Override
    public boolean existsByNombre(String nombre) {
        return jdbc.sql("SELECT COUNT(*) FROM funkos WHERE UPPER(nombre) = UPPER(:nombre)")
                .param("nombre", nombre)
                .query(Integer.class).single() > 0;
    }

    @Override
    public Funko save(Funko f) {
        var keys = new GeneratedKeyHolder();
        jdbc.sql("""
                INSERT INTO funkos (nombre, precio, cantidad, categoria, created_at, updated_at)
                VALUES (:nombre, :precio, :cantidad, :categoria, :creado, :actualizado)
                """)
                .param("nombre", f.nombre())
                .param("precio", f.precio())
                .param("cantidad", f.cantidad())
                .param("categoria", f.categoria())
                .param("creado", f.createdAt())
                .param("actualizado", f.updatedAt())
                .update(keys);

        Long id = ((Number) keys.getKeys().get("id")).longValue();
        return new Funko(id, f.nombre(), f.precio(), f.cantidad(),
                f.categoria(), f.createdAt(), f.updatedAt());
    }

    @Override
    public void update(Funko f) {
        jdbc.sql("""
                UPDATE funkos
                   SET nombre = :nombre, precio = :precio, cantidad = :cantidad,
                       categoria = :categoria, updated_at = :actualizado
                 WHERE id = :id
                """)
                .param("nombre", f.nombre())
                .param("precio", f.precio())
                .param("cantidad", f.cantidad())
                .param("categoria", f.categoria())
                .param("actualizado", f.updatedAt())
                .param("id", f.id())
                .update();
    }

    @Override
    public boolean deleteById(Long id) {
        return jdbc.sql("DELETE FROM funkos WHERE id = :id")
                .param("id", id)
                .update() == 1;
    }

    @Override
    public Map<String, Long> contarPorCategoria() {
        var filas = jdbc.sql("""
                SELECT categoria, COUNT(*) AS total
                  FROM funkos
                 GROUP BY categoria
                 ORDER BY categoria
                """)
                .query((rs, n) -> Map.entry(rs.getString("categoria"), rs.getLong("total")))
                .list();

        var resultado = new LinkedHashMap<String, Long>();
        filas.forEach(e -> resultado.put(e.getKey(), e.getValue()));
        return resultado;
    }
}