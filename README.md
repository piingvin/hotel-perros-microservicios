# Microservicio de Reservas - Hotel para Perros

Este microservicio forma parte del sistema distribuido de gestion para el Hotel Canino. Se encarga de toda la logica de negocio relacionada con la gestion de estadias, duenos y asignacion de habitaciones.

## Integrantes
* Antonia Avila (an.avilam@duocuc.cl)
* Samira Usen (sa.usen@duocuc.cl)
* Diego Saavedra (di.saavedrar@duocuc.cl)
* Oscar Inaipil (os.inaipil@duocuc.cl)

## Tecnologias y Arquitectura
* Java 17 y Spring Boot 3.3.1
* Base de Datos: MySQL (Persistencia real con JPA/Hibernate)
* Patron de Diseno: Arquitectura CSR (Controller - Service - Repository)
* Gestion de Dependencias: Maven

## Requisitos Previos
1. JDK 17 instalado y configurado en el PATH.
2. MySQL Server activo (via XAMPP o instalacion nativa) en el puerto 3306.
3. Base de datos: El sistema creara automaticamente la BD `hotel_perros_db` al iniciar la aplicacion.

## Instalacion y Ejecucion
1. Clonar el repositorio:
   `git clone https://github.com/piingvin/hotel-perros-microservicios.git`
2. Configurar el archivo `src/main/resources/application.properties` con las credenciales locales de MySQL.
3. Ejecutar el proyecto desde IntelliJ IDEA ejecutando la clase principal `HotelPerrosApplication`, o mediante consola con:
   `./mvnw spring-boot:run`

## Endpoints de la API (v1)

| Metodo | Endpoint | Descripcion |
| :--- | :--- | :--- |
| GET | `/api/v1/reservas` | Obtener todas las reservas con sus duenos. |
| GET | `/api/v1/reservas/{id}` | Buscar una reserva especifica. |
| POST | `/api/v1/reservas` | Registrar una nueva reserva (JSON requerido). |
| PUT | `/api/v1/reservas/{id}` | Actualizar datos de una estadia existente. |
| DELETE | `/api/v1/reservas/{id}` | Eliminar una reserva del sistema. |
| GET | `/api/v1/reservas/total` | Retorna el conteo total de registros. |

## Pruebas de Integracion (Postman)
Se incluyen archivos de coleccion para pruebas automaticas:
1. Importar `postman_collection.json` en Postman.
2. Configurar el environment apuntando a `http://localhost:8080`.
3. Ejecutar las peticiones para verificar el CRUD completo.

## Notas de Implementacion
* Validaciones: Se utiliza `jakarta.validation` para asegurar la integridad de los datos de entrada.
* Excepciones: Manejo centralizado de errores mediante `@RestControllerAdvice`.
* Carga Inicial: El sistema incluye un archivo `data.sql` que carga datos de prueba automaticamente al levantar el servicio por primera vez.
