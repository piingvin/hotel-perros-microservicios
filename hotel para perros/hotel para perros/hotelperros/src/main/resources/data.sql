INSERT IGNORE INTO duenos (id, nombre_completo) VALUES (1, 'Carlos Matamala');
INSERT IGNORE INTO duenos (id, nombre_completo) VALUES (2, 'Sofia Vergara');

-- Insertar reservas iniciales
INSERT IGNORE INTO reservas (id, nombre_perro, raza, dias_hospedaje, tipo_habitacion, dueno_id)
VALUES (1, 'Cachupin', 'Quiltro', 3, 'ESTANDAR', 1);

INSERT IGNORE INTO reservas (id, nombre_perro, raza, dias_hospedaje, tipo_habitacion, dueno_id)
VALUES (2, 'Zeus', 'Pastor Alemán', 7, 'VIP', 2);