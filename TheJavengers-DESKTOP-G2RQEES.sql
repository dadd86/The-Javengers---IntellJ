-- Crear tabla 'Excursiones' para almacenar información de las excursiones del centro excursionista.
-- Comentarios adicionales:
-- 1. Se usa VARCHAR(10) para 'codigo_excursion' para permitir códigos alfanuméricos cortos. Si los códigos reales son más largos, ajustar el tamaño.
-- 2. El campo 'descripcion' es obligatorio y usa el tipo TEXT porque la descripción de la excursión puede ser extensa.
-- 3. Se aplican restricciones CHECK para garantizar que los datos ingresados sean válidos:
--    - La fecha de la excursión debe ser igual o posterior a la fecha actual.
--    - El número de días debe ser mayor que cero.
--    - El precio de la inscripción debe ser positivo o cero.
-- 4. Se agrega un índice UNIQUE para evitar excursiones duplicadas con la misma descripción y fecha.
-- 5. Estas restricciones aseguran la integridad de los datos y evitan inconsistencias o entradas inválidas en la base de datos.

-- Crear tabla 'Excursiones' sin la restricción CHECK de la fecha
DROP TABLE IF EXISTS Excursiones;

CREATE TABLE Excursiones (
    codigo_excursion VARCHAR(10) PRIMARY KEY, -- Código único alfanumérico que identifica cada excursión
    descripcion TEXT NOT NULL, -- Descripción detallada de la excursión (campo obligatorio)
    fecha DATE NOT NULL, -- Fecha de la excursión
    numero_dias INT NOT NULL CHECK (numero_dias > 0), -- Número de días de la excursión, debe ser mayor que cero
    precio_inscripcion DECIMAL(10, 2) NOT NULL CHECK (precio_inscripcion >= 0), -- Precio de inscripción, no puede ser negativo
    UNIQUE (descripcion(255), fecha), -- Restricción única para evitar duplicados en la combinación de los primeros 255 caracteres de la descripción y la fecha
    CONSTRAINT CHK_precio_pos CHECK (precio_inscripcion >= 0) -- Asegura que el precio no sea negativo
);

-- Crear TRIGGER para validar que la fecha de la excursión no sea en el pasado
-- Cambiar el delimitador para crear el disparador
DELIMITER $$
CREATE TRIGGER before_insert_excursion
BEFORE INSERT ON Excursiones
FOR EACH ROW
BEGIN
    IF NEW.fecha < CURDATE() THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'La fecha de la excursión no puede ser en el pasado.';
    END IF;
END$$

-- Crear TRIGGER para las actualizaciones
CREATE TRIGGER before_update_excursion
BEFORE UPDATE ON Excursiones
FOR EACH ROW
BEGIN
    IF NEW.fecha < CURDATE() THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'La fecha de la excursión no puede ser en el pasado.';
    END IF;
END$$
-- Restaurar el delimitador original
DELIMITER ;

-- Crear tabla 'Federaciones' para almacenar los códigos y nombres de las federaciones de los socios federados.
-- Comentarios adicionales:
-- 1. 'codigo_federacion' es la clave primaria y se asegura que sea único para cada federación. Se utiliza VARCHAR(10) para códigos alfanuméricos. Si los códigos reales fueran más largos, este tamaño se puede ajustar.
-- 2. 'nombre' es un campo obligatorio y almacena el nombre de la federación. Se establece un límite de 100 caracteres para mantener la flexibilidad.
-- 3. Se añade una restricción CHECK para asegurar que 'codigo_federacion' no esté vacío. Esto previene que se inserten registros con un código vacío, asegurando que siempre haya un identificador válido.
-- 4. En esta tabla no se utiliza AUTO_INCREMENT porque los códigos de las federaciones no se generan automáticamente. Se espera que los códigos se proporcionen manualmente y sean únicos.
-- 5. En el caso de que el nombre de la federación deba ser único, se podría agregar un índice único adicional en el campo 'nombre'. Sin embargo, en esta implementación se permite que varias federaciones puedan tener nombres similares, si fuese necesario.
-- 6. Esta tabla es pequeña y simple, lo que facilita su mantenimiento y asegura una integridad básica de los datos sin una sobrecarga innecesaria de validaciones adicionales.
DROP TABLE IF EXISTS Federaciones;
CREATE TABLE Federaciones (
    codigo_federacion VARCHAR(10) PRIMARY KEY, -- Código único alfanumérico para identificar cada federación
    nombre VARCHAR(100) NOT NULL -- Nombre de la federación (campo obligatorio)
);
-- Crear tabla 'Socios' para almacenar la información de los socios del centro excursionista.
-- Comentarios adicionales:
-- 1. Se usa AUTO_INCREMENT para 'numero_socio', permitiendo que MySQL genere automáticamente un identificador único para cada socio.
-- 2. El campo 'nombre' es obligatorio, por lo que debe tener al menos un valor no nulo.
-- 3. El campo 'nif' es opcional (puede ser NULL), ya que no es obligatorio para socios infantiles.
-- 4. El campo 'tipo_socio' es obligatorio y define el tipo de socio, lo que permite gestionar las características y restricciones de cada tipo.
-- 5. 'seguro_contratado' solo es obligatorio para los socios estándar. Para los federados y los infantiles, este campo puede ser NULL.
-- 6. 'numero_padre_o_madre' solo aplica a los socios infantiles, y establece una relación jerárquica con otro socio (su padre o madre).
-- 7. 'federacion_codigo' solo aplica a socios federados. Se relaciona con la tabla 'Federaciones'.
-- 8. Las claves foráneas están configuradas con 'ON DELETE SET NULL' para evitar la eliminación de registros dependientes y evitar conflictos de integridad.
-- 9. 'cuota_mensual' está predefinida en 10€, pero se puede ajustar en función de las reglas de negocio. Se utiliza DECIMAL para asegurar la precisión en las cantidades monetarias.
-- 10. Las restricciones ON DELETE y ON UPDATE aseguran que si los registros relacionados son eliminados o modificados, los datos en esta tabla se actualizan o ponen en NULL automáticamente para mantener la integridad.
DROP TABLE IF EXISTS Socios;
CREATE TABLE Socios (
    numero_socio INT AUTO_INCREMENT PRIMARY KEY, -- Identificador único de cada socio, generado automáticamente
    nombre VARCHAR(100) NOT NULL, -- Nombre del socio, campo obligatorio
    nif VARCHAR(15) DEFAULT NULL, -- Número de identificación fiscal (NIF), puede no estar presente para los socios infantiles
    tipo_socio ENUM('estandar', 'federado', 'infantil') NOT NULL, -- Tipo de socio: estándar, federado o infantil (campo obligatorio)
    seguro_contratado ENUM('basico', 'completo') DEFAULT NULL, -- Seguro contratado para los socios estándar, no aplicable a los federados o infantiles
    numero_padre_o_madre INT DEFAULT NULL, -- Número de socio del padre o madre, solo para los socios infantiles
    federacion_codigo VARCHAR(10) DEFAULT NULL, -- Código de la federación, aplicable solo a socios federados
    cuota_mensual DECIMAL(10, 2) NOT NULL DEFAULT 10.00, -- Cuota mensual del socio, 10€ por defecto con posibles descuentos aplicables
    FOREIGN KEY (numero_padre_o_madre) REFERENCES Socios(numero_socio)
        ON DELETE SET NULL ON UPDATE CASCADE, -- Si el padre o madre es eliminado, este valor se pone a NULL
    FOREIGN KEY (federacion_codigo) REFERENCES Federaciones(codigo_federacion)
        ON DELETE SET NULL ON UPDATE CASCADE -- Si la federación es eliminada, este valor se pone a NULL
);
-- Crear tabla 'Inscripciones' para almacenar las inscripciones de los socios en las excursiones.
-- Comentarios adicionales:
-- 1. 'numero_inscripcion' es la clave primaria y se genera automáticamente mediante AUTO_INCREMENT, lo que garantiza que cada inscripción tenga un identificador único.
-- 2. 'numero_socio' y 'codigo_excursion' son campos obligatorios, asegurando que cada inscripción esté relacionada con un socio y una excursión.
-- 3. Se añade una restricción CHECK para asegurar que 'fecha_inscripcion' no sea en el pasado, garantizando que solo se registren inscripciones válidas en el sistema.
-- 4. Las claves foráneas 'numero_socio' y 'codigo_excursion' están enlazadas a las tablas 'Socios' y 'Excursiones', respectivamente. Se utilizan ON DELETE CASCADE y ON UPDATE CASCADE para mantener la integridad referencial:
   -- Si se elimina un socio o una excursión, sus inscripciones correspondientes se eliminan automáticamente (ON DELETE CASCADE).
   -- Si se actualizan los datos de un socio o una excursión, las inscripciones asociadas se actualizan automáticamente (ON UPDATE CASCADE).
-- 5. 'fecha_inscripcion' tiene como valor predeterminado la fecha actual (CURRENT_DATE), lo que permite registrar inscripciones automáticamente con la fecha del día en que se insertan, si no se especifica otra.
-- 6. En caso de que quieras evitar múltiples inscripciones del mismo socio a la misma excursión, puedes agregar un índice único compuesto entre 'numero_socio' y 'codigo_excursion':
    -- `UNIQUE (numero_socio, codigo_excursion)` evita que el mismo socio se inscriba más de una vez a la misma excursión.
DROP TABLE IF EXISTS Inscripciones;
CREATE TABLE Inscripciones (
    numero_inscripcion INT AUTO_INCREMENT PRIMARY KEY, -- Identificador único de cada inscripción, generado automáticamente
    numero_socio INT NOT NULL, -- Número de socio que se inscribe en la excursión (obligatorio)
    codigo_excursion VARCHAR(10) NOT NULL, -- Código de la excursión a la que se inscribe el socio (obligatorio)
    fecha_inscripcion DATE NOT NULL, -- Fecha de inscripción, obligatoria
    FOREIGN KEY (numero_socio) REFERENCES Socios(numero_socio) 
        ON DELETE CASCADE ON UPDATE CASCADE, -- Si se elimina un socio, se eliminan sus inscripciones
    FOREIGN KEY (codigo_excursion) REFERENCES Excursiones(codigo_excursion)
        ON DELETE CASCADE ON UPDATE CASCADE -- Si se elimina una excursión, se eliminan las inscripciones asociadas
);


-- Usa este TRIGGER para asegurar que la fecha de inscripción no sea anterior a la fecha actual:
DELIMITER $$
CREATE TRIGGER before_insert_inscripcion
BEFORE INSERT ON Inscripciones
FOR EACH ROW
BEGIN
    IF NEW.fecha_inscripcion < CURDATE() THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'La fecha de inscripción no puede ser en el pasado.';
    END IF;
END$$
DELIMITER ;


-- Si también quieres asegurarte de que las actualizaciones no permitan fechas de inscripción en el pasado, usa este TRIGGER:
DELIMITER $$
CREATE TRIGGER before_update_inscripcion
BEFORE UPDATE ON Inscripciones
FOR EACH ROW
BEGIN
    IF NEW.fecha_inscripcion < CURDATE() THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'La fecha de inscripción no puede ser en el pasado.';
    END IF;
END$$
DELIMITER ;

-- Crear tabla 'Seguros' para almacenar los tipos de seguros disponibles y sus precios.
-- Comentarios adicionales:
-- 1. 'tipo_seguro' se define como ENUM con los valores 'basico' y 'completo', asegurando que solo se puedan usar estos dos valores. Además, se establece como PRIMARY KEY porque cada tipo de seguro es único.
-- 2. Se utiliza DECIMAL(10, 2) para 'precio' para asegurar precisión en las cantidades monetarias (hasta dos decimales) y que no haya errores en la gestión de precios.
-- 3. Se agrega una restricción CHECK en 'precio' para asegurar que el valor siempre sea positivo o cero, evitando errores de datos.
-- 4. Esta tabla es pequeña y sencilla, por lo que no se requiere AUTO_INCREMENT u otras optimizaciones más avanzadas, pero se han añadido medidas para garantizar la integridad de los datos.
DROP TABLE IF EXISTS Seguros;
CREATE TABLE Seguros (
    tipo_seguro ENUM('basico', 'completo') PRIMARY KEY, -- Tipo de seguro: básico o completo (valores fijos)
    precio DECIMAL(10, 2) NOT NULL CHECK (precio >= 0) -- Precio del seguro, no puede ser negativo
);
