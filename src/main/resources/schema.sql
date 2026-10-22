CREATE TABLE IF NOT EXISTS funkos (
                                      id         BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      nombre     VARCHAR(100)  NOT NULL,
    precio     DECIMAL(10,2) NOT NULL,
    cantidad   INT           NOT NULL DEFAULT 0,
    categoria  VARCHAR(20)   NOT NULL,
    created_at TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_funko_nombre UNIQUE (nombre),
    CONSTRAINT ck_funko_precio   CHECK (precio   >= 0),
    CONSTRAINT ck_funko_cantidad CHECK (cantidad >= 0),
    CONSTRAINT ck_funko_categoria
    CHECK (categoria IN ('DISNEY','MARVEL','ANIME','OTROS'))
    );

CREATE INDEX IF NOT EXISTS idx_funko_categoria ON funkos (categoria);