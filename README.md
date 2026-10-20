# Inventory

Sistema de gestión de inventario de activos tecnológicos desarrollado con Spring Boot y Java 17.

## Descripción

Esta aplicación permite administrar activos tecnológicos de una organización, incluyendo el registro, actualización de estados, consulta de inventario y gestión de catálogos. La solución sigue una arquitectura basada en Spring Boot con capas de controladores, servicios, entidades, repositorios y DTOs.

## Tecnologías

- Java 17
- Spring Boot 3.5.16
- Spring Web
- Spring Data JPA / JDBC
- Spring Security
- JWT
- Oracle Database
- Springdoc OpenAPI / Swagger
- Apache POI

## Estructura del proyecto

```text
src/
├── main/
│   ├── java/com/exam/inventory
│   │   ├── common
│   │   ├── config
│   │   ├── controller
│   │   ├── dtos
│   │   ├── entity
│   │   ├── repository
│   │   ├── service
│   │   └── InventoryApplication.java
│   └── resources/
│       └── application.properties
└── test/
```

## Requisitos

- Java 17
- Maven
- Oracle Database accesible localmente
- Acceso a la PDB `FREEPDB1` o un servicio equivalente

## Ejecutar la aplicación

```bash
./mvnw clean package
./mvnw spring-boot:run
```

## Base de datos

El script SQL para crear la estructura base y datos iniciales del sistema se encuentra en:

```text
database/inventory_schema.sql
```

Ese script crea:
- catálogo de categorías (`CAT_CATEGORY`)
- catálogo de estados (`CAT_STATUS`)
- tabla principal de inventario (`TAB_TECHNOLOGICAL_ASSETS`)
- secuencia para folios (`SEQ_INVENTORY_FOLIO`)
- función y trigger para generar folios automáticos
- registros iniciales de categorías y estados

## Documentación API

La aplicación expone documentación Swagger/OpenAPI. Normalmente se accede en:

```text
http://localhost:8080/swagger-ui/index.html
```

## Notas

- La configuración de conexión a la base de datos está en `src/main/resources/application.properties`.
- La aplicación usa autenticación mediante Spring Security y JWT.
- Los activos tecnológicos tienen folio generado automáticamente según la categoría asignada.
