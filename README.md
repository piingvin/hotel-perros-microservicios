# Hotel para Perros - Arquitectura de Microservicios

Sistema distribuido de gestión de un hotel canino con **10 microservicios independientes**, comunicación REST, API Gateway, pruebas unitarias, documentación Swagger/OpenAPI y despliegue con Docker.

## Integrantes


- Oscar Inaipil (os.inaipil@duocuc.cl)
-  Antonia Avila (an.avilam@duocuc.cl)
-  Samira Usen (sa.usen@duocuc.cl)
 - Diego Saavedra (di.saavedrar@duocuc.cl)

## Microservicios implementados

| # | Servicio | Puerto | Base path | Swagger |
|---|----------|--------|-----------|---------|
| 1 | **ms-reservas** | 8080 | `/api/v1/reservas` | http://localhost:8080/swagger-ui.html |
| 2 | ms-duenos | 8082 | `/api/v1/duenos` | http://localhost:8082/swagger-ui.html |
| 3 | ms-mascotas | 8083 | `/api/v1/mascotas` | http://localhost:8083/swagger-ui.html |
| 4 | ms-habitaciones | 8084 | `/api/v1/habitaciones` | http://localhost:8084/swagger-ui.html |
| 5 | **ms-facturacion** | 8085 | `/api/v1/facturacion` | http://localhost:8085/swagger-ui.html |
| 6 | ms-empleados | 8086 | `/api/v1/empleados` | http://localhost:8086/swagger-ui.html |
| 7 | ms-servicios | 8087 | `/api/v1/servicios` | http://localhost:8087/swagger-ui.html |
| 8 | ms-inventario | 8088 | `/api/v1/inventario` | http://localhost:8088/swagger-ui.html |
| 9 | ms-notificaciones | 8089 | `/api/v1/notificaciones` | http://localhost:8089/swagger-ui.html |
| 10 | ms-reportes | 8090 | `/api/v1/reportes` | http://localhost:8090/swagger-ui.html |

## API Gateway (Spring Cloud Gateway)

| Componente | Puerto | Descripción |
|------------|--------|-------------|
| **api-gateway** | 8081 | Punto de entrada único para todos los microservicios |

### Rutas del Gateway

| Ruta | Microservicio destino |
|------|----------------------|
| `/api/v1/reservas/**` | ms-reservas (8080) |
| `/api/v1/duenos/**` | ms-duenos (8082) |
| `/api/v1/mascotas/**` | ms-mascotas (8083) |
| `/api/v1/habitaciones/**` | ms-habitaciones (8084) |
| `/api/v1/facturacion/**` | ms-facturacion (8085) |
| `/api/v1/empleados/**` | ms-empleados (8086) |
| `/api/v1/servicios/**` | ms-servicios (8087) |
| `/api/v1/inventario/**` | ms-inventario (8088) |
| `/api/v1/notificaciones/**` | ms-notificaciones (8089) |
| `/api/v1/reportes/**` | ms-reportes (8090) |

Ejemplo vía Gateway: `http://localhost:8081/api/v1/reservas`

## Tecnologías

- Java 17, Spring Boot 3.3.1
- Patrón CSR (Controller - Service - Repository)
- H2 Database (memoria)
- SpringDoc OpenAPI (Swagger UI)
- WebClient (comunicación ms-facturacion → ms-reservas)
- Spring Cloud Gateway
- JUnit 5 + Mockito (pruebas unitarias, JaCoCo ≥80%)
- Docker + Docker Compose

## Comunicación entre microservicios

**ms-facturacion** consume **ms-reservas** via WebClient:
- Al crear una factura con `reservaId`, consulta `GET /api/v1/reservas/{id}/costo`
- El monto se calcula automáticamente según tipo de habitación (VIP $50/día, ESTANDAR $25/día)

## Ejecución local

### Requisitos
- JDK 17
- Maven 3.8+

### Opción 1: Script batch (Windows)
```bat
EJECUTAR_TODOS_MS.bat
```

### Opción 2: Manual (cada microservicio)
```bash
cd microservices/ms-reservas && mvn spring-boot:run
cd microservices/ms-duenos && mvn spring-boot:run
# ... repetir para cada servicio
cd api-gateway && mvn spring-boot:run
```

### Opción 3: Docker Compose
```bash
docker-compose up --build
```

## Pruebas unitarias

```bash
cd microservices/ms-reservas
mvn test

# Reporte de cobertura JaCoCo
mvn verify
# Ver: target/site/jacoco/index.html
```

## Perfiles YAML

Cada microservicio usa perfiles `dev` (local) y `prod` (Docker/despliegue remoto):
- `dev`: H2 en memoria, puertos fijos
- `prod`: variables de entorno (`PORT`, `DATABASE_URL`, `RESERVAS_API_URL`)

## Endpoints principales de Reservas

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/v1/reservas` | Listar reservas |
| POST | `/api/v1/reservas` | Crear reserva |
| GET | `/api/v1/reservas/{id}/costo` | Calcular costo |
| GET | `/api/v1/reservas/total` | Total de reservas |

## Prueba de integración (Facturación + Reservas)

```bash
curl http://localhost:8080/api/v1/reservas/1/costo
curl -X POST http://localhost:8085/api/v1/facturacion \
  -H "Content-Type: application/json" \
  -d "{\"concepto\":\"Hospedaje\",\"reservaId\":1}"
```
