-- Deshabilitar restricciones de claves foráneas temporalmente
SET FOREIGN_KEY_CHECKS = 0;

-- Eliminar las tablas en el orden adecuado
DROP TABLE IF EXISTS `inscripciones`;
DROP TABLE IF EXISTS `socios`;
DROP TABLE IF EXISTS `seguros`;
DROP TABLE IF EXISTS `excursiones`;
DROP TABLE IF EXISTS `federaciones`;

-- Rehabilitar las restricciones de claves foráneas
SET FOREIGN_KEY_CHECKS = 1;
