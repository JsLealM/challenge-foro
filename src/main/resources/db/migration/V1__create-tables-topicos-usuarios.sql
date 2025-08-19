create table usuarios (
    id bigint not null auto_increment,
    nombre varchar(100) not null,
    correo_electronico varchar(100) not null unique,
    contrasena varchar(255) not null,
    primary key (id)
);

CREATE TABLE topicos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(100) NOT NULL,
    mensaje VARCHAR(500) NOT NULL,
    fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    estado TINYINT NOT NULL DEFAULT 1,
    autor BIGINT NOT NULL,
    curso VARCHAR(50) NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT fk_topicos_autor FOREIGN KEY (autor) REFERENCES usuarios(id),
    CONSTRAINT uq_topicos_titulo_mensaje UNIQUE (titulo, mensaje)
);

