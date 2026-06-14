@echo off
REM Script para ejecutar todos los microservicios del Hotel para Perros
REM Requiere: Java 17, Maven
REM Uso: Ejecutar este archivo desde la carpeta raiz del proyecto

setlocal enabledelayedexpansion

echo.
echo ============================================
echo   Hotel para Perros - Microservicios
echo   Iniciando 10 servicios + API Gateway...
echo ============================================
echo.

where mvn >nul 2>nul
if %errorlevel% neq 0 (
    echo ERROR: Maven no encontrado en PATH
    pause
    exit /b 1
)

if not exist "logs" mkdir logs

echo.
echo [PASO 1] Compilando microservicios...
echo.

for %%D in (ms-reservas ms-duenos ms-mascotas ms-habitaciones ms-facturacion ms-empleados ms-servicios ms-inventario ms-notificaciones ms-reportes) do (
    echo Compilando %%D...
    cd microservices\%%D
    call mvn clean compile -q
    if !errorlevel! neq 0 (
        echo ERROR: Fallo compilacion de %%D
        pause
        exit /b 1
    )
    cd ..\..
)

echo Compilando api-gateway...
cd api-gateway
call mvn clean compile -q
if %errorlevel% neq 0 (
    echo ERROR: Fallo compilacion de api-gateway
    pause
    exit /b 1
)
cd ..

echo [OK] Todos los servicios compilados

echo.
echo ============================================
echo   INICIANDO MICROSERVICIOS EN NUEVAS VENTANAS
echo ============================================
echo.

echo [PUERTO 8080] MS Reservas
start "MS-Reservas-8080" cmd /k "cd microservices\ms-reservas && mvn spring-boot:run"
timeout /t 5 /nobreak

echo [PUERTO 8082] MS Duenos
start "MS-Duenos-8082" cmd /k "cd microservices\ms-duenos && mvn spring-boot:run"
timeout /t 3 /nobreak

echo [PUERTO 8083] MS Mascotas
start "MS-Mascotas-8083" cmd /k "cd microservices\ms-mascotas && mvn spring-boot:run"
timeout /t 3 /nobreak

echo [PUERTO 8084] MS Habitaciones
start "MS-Habitaciones-8084" cmd /k "cd microservices\ms-habitaciones && mvn spring-boot:run"
timeout /t 3 /nobreak

echo [PUERTO 8085] MS Facturacion (integracion con Reservas)
start "MS-Facturacion-8085" cmd /k "cd microservices\ms-facturacion && mvn spring-boot:run"
timeout /t 3 /nobreak

echo [PUERTO 8086] MS Empleados
start "MS-Empleados-8086" cmd /k "cd microservices\ms-empleados && mvn spring-boot:run"
timeout /t 3 /nobreak

echo [PUERTO 8087] MS Servicios Extra
start "MS-Servicios-8087" cmd /k "cd microservices\ms-servicios && mvn spring-boot:run"
timeout /t 3 /nobreak

echo [PUERTO 8088] MS Inventario
start "MS-Inventario-8088" cmd /k "cd microservices\ms-inventario && mvn spring-boot:run"
timeout /t 3 /nobreak

echo [PUERTO 8089] MS Notificaciones
start "MS-Notificaciones-8089" cmd /k "cd microservices\ms-notificaciones && mvn spring-boot:run"
timeout /t 3 /nobreak

echo [PUERTO 8090] MS Reportes
start "MS-Reportes-8090" cmd /k "cd microservices\ms-reportes && mvn spring-boot:run"
timeout /t 3 /nobreak

echo [PUERTO 8081] API Gateway
start "API-Gateway-8081" cmd /k "cd api-gateway && mvn spring-boot:run"
timeout /t 3 /nobreak

echo.
echo ============================================
echo   TODOS LOS MICROSERVICIOS INICIADOS
echo ============================================
echo.
echo API Gateway:     http://localhost:8081/api/v1/reservas
echo MS Reservas:     http://localhost:8080/swagger-ui.html
echo MS Facturacion:  http://localhost:8085/swagger-ui.html
echo.
echo CURL - Prueba de integracion:
echo   curl http://localhost:8080/api/v1/reservas
echo   curl -X POST http://localhost:8085/api/v1/facturacion -H "Content-Type: application/json" -d "{\"concepto\":\"Hospedaje\",\"reservaId\":1}"
echo.
pause
