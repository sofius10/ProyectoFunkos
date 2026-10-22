package com.daw2.funkos.repositories;


import com.daw2.funkos.models.ProductoX;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;


import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProductoRowMapper implements RowMapper<ProductoX> {


    @Override
    public ProductoX mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ProductoX(
                rs.getLong("id"),
                rs.getString("nombre"),
                rs.getBigDecimal("precio")
        );
    }
}
