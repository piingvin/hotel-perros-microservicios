# Microservicio de Reservas - Hotel para Perros

Este microservicio forma parte del sistema distribuido de gestión para el Hotel Canino. Se encarga de toda la lógica de negocio relacionada con la gestión de estadías, dueños, asignación de habitaciones y facturación básica.

## Integrantes
* Antonia Avila (an.avilam@duocuc.cl)
* Samira Usen (sa.usen@duocuc.cl)
* Diego Saavedra (di.saavedrar@duocuc.cl)
* Oscar Inaipil (os.inaipil@duocuc.cl)

## Tecnologías y Arquitectura
* **Java 17** y **Spring Boot 3.3.1**
* **Base de Datos:** MySQL (Persistencia real con JPA/Hibernate)
* **Patrón de Diseño:** Arquitectura CSR (Controller - Service - Repository)
* **Herramientas Adicionales:** Lombok (v1.18.32) para reducción de código boilerplate.
* **Gestión de Dependencias:** Maven

## Requisitos Previos
1. JDK 17 instalado y configurado en el PATH.
2. MySQL Server activo (vía XAMPP o instalación nativa) en el puerto 3306.
3. Base de datos: El sistema creará automáticamente la BD `hotel_perros_db` al iniciar la aplicación.

## Instalación y Ejecución
1. Clonar el repositorio:
   `git clone https://github.com/piingvin/hotel-perros-microservicios.git`
2. Configurar el archivo `src/main/resources/application.properties` con las credenciales locales de MySQL.
3. Ejecutar el proyecto desde IntelliJ IDEA iniciando la clase principal `HotelPerrosApplication`, o mediante consola con:
   `./mvnw spring-boot:run`

## Endpoints de la API (v1)

| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| GET | `/api/v1/reservas` | Obtener todas las reservas activas con la información de sus dueños. |
| POST | `/api/v1/reservas` | Registrar una nueva reserva (JSON requerido con validaciones). |
| GET | `/api/v1/reservas/{id}` | Buscar los detalles de una reserva específica. |
| PUT | `/api/v1/reservas/{id}` | Actualizar la información completa de una estadía existente. |
| DELETE | `/api/v1/reservas/{id}` | Eliminar una reserva del sistema mediante su ID. |
| GET | `/api/v1/reservas/total` | Retorna la cantidad total de reservas registradas en el hotel. |
| GET | `/api/v1/reservas/buscar?nombre={nombre}` | Busca reservas según el nombre de la mascota (coincidencia parcial). |
| GET | `/api/v1/reservas/habitacion/{tipo}` | Filtra las estadías activas por tipo de habitación (VIP o ESTANDAR). |
| GET | `/api/v1/reservas/rango?fechaInicio={f1}&fechaFin={f2}` | Filtra las reservas que se encuentren dentro de un rango de fechas. |
| GET | `/api/v1/reservas/{id}/costo` | Calcula el costo total de la reserva según tarifa de habitación y días de hospedaje. |

## Pruebas de Integración (Postman)
Se incluyen archivos de colección para pruebas automáticas:
1. Importar `postman_collection.json` en Postman.
2. Configurar el environment apuntando a `http://localhost:8080`.
3. Ejecutar las peticiones para verificar el CRUD completo y las lógicas de negocio.

## Notas de Implementación
* **Validaciones:** Se utiliza `jakarta.validation` para asegurar la integridad de los datos de entrada (ej. días positivos, campos no vacíos).
* **Excepciones:** Manejo centralizado de errores mediante `@RestControllerAdvice` para devolver respuestas HTTP limpias y estandarizadas.
* **Fechas y Costos:** Incorporación de `LocalDate` para el control temporal y lógica de cálculo de tarifas según categoría de la habitación.
