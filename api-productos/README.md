# api-productos

API REST de Inventario construida con Spring Boot, siguiendo el taller práctico guiado
de Desarrollo Web Back-End (arquitectura en capas: Model, Repository, Service, Controller).

## Cómo abrirlo en IntelliJ

1. Descomprime el archivo `.zip`.
2. En IntelliJ: `File > Open...` y selecciona la carpeta `api-productos` (la que contiene `pom.xml`).
3. IntelliJ detectará que es un proyecto Maven y descargará las dependencias automáticamente
   (necesitas conexión a internet la primera vez).
4. Verifica que el SDK del proyecto sea Java 17 (`File > Project Structure > Project SDK`).
5. Ejecuta la clase `ApiProductosApplication` (botón ▶ o clic derecho > Run).
6. La API quedará disponible en `http://localhost:8080`.

## Probar los endpoints

- Usa el archivo `Pruebas_HTTP.http` con el plugin "HTTP Client" de IntelliJ (viene incluido),
  o importa las mismas solicitudes en Postman/Bruno.
- Consola H2 disponible en `http://localhost:8080/h2-console`
  (JDBC URL: `jdbc:h2:mem:inventariodb`, usuario: `sa`, sin contraseña).

## Estructura

```
src/main/java/com/inventario/api_productos/
 ├── model/Producto.java
 ├── repository/ProductoRepository.java
 ├── service/ProductoService.java
 ├── controller/ProductoController.java
 └── exception/StockInsuficienteException.java
src/main/resources/application.properties
src/main/resources/application-mysql.properties
src/main/resources/application-postgresql.properties
Pruebas_HTTP.http
```

## Retos evaluativos incluidos

- **Reto 1 (consulta personalizada)**: `GET /api/productos/precio-menor?precio=200`
  usa `findByPrecioLessThan` en el repositorio.
- **Reto 2 (reducir stock)**: `PATCH /api/productos/{id}/reducir-stock?cantidad=X`
  descuenta del inventario y responde `400 Bad Request` si el stock es insuficiente.
- **Reto 3 (persistencia externa)**: por defecto el proyecto sigue usando H2 en memoria
  (para que corra sin configurar nada). Para usar MySQL o PostgreSQL de verdad:
  1. Instala el motor y crea la base de datos `inventariodb`.
  2. Revisa/ajusta usuario y contraseña en `application-mysql.properties` o
     `application-postgresql.properties`.
  3. Ejecuta con el perfil activo, por ejemplo en IntelliJ (Run > Edit Configurations >
     "Active profiles": `mysql`) o por línea de comandos:
     `mvn spring-boot:run -Dspring-boot.run.profiles=mysql`
