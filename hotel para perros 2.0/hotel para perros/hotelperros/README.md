# Hotel para Perros - API REST

Proyecto Spring Boot listo para ejecutar en IntelliJ IDEA.

## Requisitos

- JDK 17 o superior
- IntelliJ IDEA (Community o Ultimate)
- Maven Wrapper (incluido en el proyecto)

## Abrir en IntelliJ

1. Abre IntelliJ IDEA.
2. Selecciona `Open` y elige la carpeta `hotelperros`.
3. Espera a que IntelliJ detecte el proyecto Maven (`pom.xml`) y descargue dependencias.
4. Verifica que el SDK del proyecto sea Java 17+.

## Ejecutar la API

1. Abre la clase `HotelPerrosApplication`.
2. Ejecuta el metodo `main`.
3. La API quedara disponible en:
   - `http://localhost:8080/api/v1/reservas`

## Endpoints disponibles

- `GET /api/v1/reservas` -> Lista todas las reservas
- `GET /api/v1/reservas/{id}` -> Busca una reserva por id
- `POST /api/v1/reservas` -> Crea una reserva
- `PUT /api/v1/reservas/{id}` -> Actualiza una reserva
- `DELETE /api/v1/reservas/{id}` -> Elimina una reserva
- `GET /api/v1/reservas/total` -> Total de reservas

## Ejemplo para POST (JSON)

```json
{
  "id": 11,
  "nombrePerro": "Toby",
  "raza": "Beagle",
  "nombreDueno": "Carla Reyes",
  "diasHospedaje": 4,
  "tipoHabitacion": "Premium"
}
```

## Probar rapido en terminal (PowerShell)

```powershell
Invoke-RestMethod -Method Get -Uri "http://localhost:8080/api/v1/reservas"
```

## Compilar por consola

```powershell
./mvnw clean package
```

## Probar con Postman (recomendado)

1. Importa `postman_collection.json`.
2. Importa `local.postman_environment.json`.
3. Selecciona el environment `HotelPerros Local`.
4. Ejecuta las requests en este orden:
   - `Listar reservas`
   - `Buscar reserva por ID`
   - `Crear reserva`
   - `Actualizar reserva`
   - `Total reservas`
   - `Eliminar reserva`

## Notas tecnicas

- Arquitectura CSR: `controller`, `service`, `repository`, `model`.
- Validaciones con Bean Validation (`@Valid` y constraints en modelo).
- Manejo global de errores con `@RestControllerAdvice`.
- Logs de servicio con SLF4J.
