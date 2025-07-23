
# ShipFlow

Microservicio monolítico en **Spring Boot + Kotlin** para gestionar el **flujo de envíos de paquetes** de forma clara y organizada.

---

## 📦 Descripción del Proyecto

ShipFlow permite:

✅ Crear un **nuevo envío** con datos obligatorios  
✅ Consultar todos los **envíos registrados**  
✅ **Actualizar el estado** del envío validando las transiciones permitidas  
✅ Consultar el historial de cambios de estado de cada envío

---

## 🚀 Tecnologías

- Kotlin
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Gradle
- Postman para pruebas

---

## 🖥️ Configuración Local

### 1️⃣ PostgreSQL Local

- Asegúrate de tener **PostgreSQL instalado y ejecutándose** localmente.
- Crea una base de datos llamada:

```sql
CREATE DATABASE shipflow_db;
```

- Crea el usuario (si no existe) y dale permisos:

```sql
CREATE USER postgres WITH PASSWORD '1234';
GRANT ALL PRIVILEGES ON DATABASE shipflow_db TO postgres;
```

---

### 2 Ejecutar la aplicación

```bash
./gradlew bootRun
```

La API quedará disponible en:

```
http://localhost:8084
```

---

## 📮 Endpoints para pruebas en Postman

### ➕ Crear envío

**POST** `http://localhost:8084/shipments`

```json
{
    "packageType": "BOX",
    "description": "Libros de Kotlin",
    "weight": 2.5,
    "originCity": "Quito",
    "destinationCity": "Guayaquil"
}
```

---

### 📋 Listar todos los envíos

**GET** `http://localhost:8084/shipments`

---

### 🔄 Cambiar estado de un envío

**PUT** `http://localhost:8084/shipments/{trackingId}/status`

Body:
```json
{
    "newStatus": "IN_TRANSIT"
}
```

Estados permitidos:
- CREATED
- IN_TRANSIT
- DELIVERED

---

### 🕓 Consultar historial de estados (si implementado en futuras extensiones)

**GET** `http://localhost:8084/shipments/{trackingId}/history`

---


