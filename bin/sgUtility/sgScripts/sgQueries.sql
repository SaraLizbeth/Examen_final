-- Revisar el entorno de trabajo
.version
.database
.show
.tables

--QUERIES PARA CREAR LAS TABLAS
--TABLA

--Queries de usuarios con claves encriptadas con MD5
INSERT INTO SG_USUARIOS (Usuario, Contrasenia) VALUES ("profe", "827ccb0eea8a706c4c34a16891f84e7b");
INSERT INTO SG_USUARIOS (Usuario, Contrasenia) VALUES ("sara.guayasamin01@epn.edu.ec", "3f2e0a13579ad9e8d83885deb4263b04");
INSERT INTO SG_USUARIOS (Usuario, Contrasenia) VALUES ("pepito.lopez@epn.edu.ec", "396f9c44118efe3602d9512d9dd9df9a");

CREATE TABLE IF NOT EXISTS  SG_USUARIOS
(
    IdUsuario INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    Usuario VARCHAR(30) UNIQUE NOT NULL,
    Contrasenia VARCHAR(10) NOT NULL
);

-- Tabla ArsenalTipo
CREATE TABLE IF NOT EXISTS ArsenalTipo (
    ID INTEGER PRIMARY KEY AUTOINCREMENT,
    ArsenalTipo VARCHAR(20)
);


-- Tabla Horarios
CREATE TABLE IF NOT EXISTS Horarios (
    ID INTEGER PRIMARY KEY AUTOINCREMENT,
    Lunes VARCHAR(10),
    Martes VARCHAR(10),
    Miercoles VARCHAR(10),
    Jueves VARCHAR(10),
    Viernes VARCHAR(10)

);

CREATE TABLE IF NOT EXISTS HoraAtaque (
    ID INTEGER PRIMARY KEY AUTOINCREMENT,
    HoraAtaque VARCHAR(10)
);




CREATE TABLE IF NOT EXISTS SG_JOIN (
    sgHoraAtaque VARCHAR (10),
    sgLunes VARCHAR(10),
    sgMartes VARCHAR(10),
    sgMiercoles VARCHAR(10),
    sgJueves VARCHAR(10),
    sgViernes VARCHAR(10)

);

-- QUERIES PARA BORRAR DATOS

DROP TABLE HoraAtaque;
DROP TABLE ArsenalTipo;
DROP TABLE Horarios;
DROP TABLE SG_JOIN;


-- Join de las tablas 
SELECT
    ct.ArsenalTipo AS "Tipo Arsenal",
    c.Coordenada,
    a.Arsenal AS Arsenal,
    h.Dia,
    h.Hora
FROM
    ArsenalTipo ct
JOIN
    Coordenada c ON c.id = ct.id
JOIN
    Arsenal a ON a.id = c.id
JOIN
    Horarios h ON h.id = c.id;


