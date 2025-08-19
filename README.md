# ForoHub API 🗨️

ForoHub es una API REST desarrollada con **Java 21** y **Spring Boot** que simula el backend de un foro académico.  
Permite registrar, actualizar, listar y eliminar **tópicos** creados por los usuarios, aplicando reglas de negocio y buenas prácticas de desarrollo.

---

## 🚀 Tecnologías utilizadas

- **Java 21**
- **Spring Boot 3**
- **Spring Data JPA**
- **Spring Security con JWT**
- **Flyway** (Migraciones de base de datos)
- **MySQL**
- **Maven**

---

## 📌 Funcionalidades principales

- **Autenticación con JWT** (inicio de sesión con correo y contraseña).
- **Gestión de tópicos**:
    - Crear nuevo tópico (sin permitir duplicados por título + mensaje).
    - Listar todos los tópicos.
    - Actualizar tópico existente (validando ID y reglas de negocio).
    - Eliminar un tópico por ID.
    - Consultar un tópico por ID.
- Manejo global de errores con `@RestControllerAdvice`.
- Migraciones automáticas de base de datos con **Flyway**.

---

## 📂 Estructura del proyecto

```
foro-hub/
│── src/main/java/com/alura/foro_hub/
│   ├── controller/      # Controladores REST
│   ├── domain/          # Entidades JPA y DTOs
│   ├── infra/           # Seguridad, JWT y excepciones
│   └── ForoHubApplication.java
│
│── src/main/resources/
│   ├── db/migration/    # Scripts SQL con Flyway
│   └── application.properties
│
└── pom.xml
```

---

## ⚙️ Configuración

### 1️⃣ Clonar el repositorio

```bash
git clone https://github.com/JsLealM/challenge-foro.git
cd challenge-foro
```

### 2️⃣ Configurar la base de datos

Crea una base de datos en MySQL:

```sql
CREATE DATABASE challenge_foro;
```

En el archivo `src/main/resources/application.properties` configura tus credenciales:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/challenge_foro
spring.datasource.username=root
spring.datasource.password=root

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.flyway.enabled=true

api.security.token.secret=mi_clave_secreta_segura
api.security.token.expiration=2 (horas de expiracion)
```

### 3️⃣ Ejecutar migraciones con Flyway

```bash
./mvnw flyway:migrate
```

### 4️⃣ Ejecutar la aplicación

```bash
./mvnw spring-boot:run
```

La API estará disponible en:  
👉 `http://localhost:8080`

---

## 🔑 Autenticación

1. Registra un **usuario** en la base de datos (tabla `usuarios`) haciendo insert manualmente.
```sql
insert into usuarios values(1, 'Nombre Usuario', 'correo@mail.com', 'contrasena Bcrypt');
```
 Puedes usar esta contraseña como ejemplo `$2a$10$Y50UaMFOxteibQEYLrwuHeehHYfcoafCopUazP12.rqB41bsolF5.` la contraseña es: `123456`
2. Haz una petición **POST** a `/login` con JSON:

```json
{
  "correo_electronico": "correo@mail.com",
  "contrasena": "123456"
}
```

3. Obtendrás un **token JWT** que deberás enviar en el header `Authorization`:

```
Authorization: Bearer <token>
```

---

## 📖 Endpoints principales

### 🔐 Autenticación
- `POST /login` → Genera un JWT

### 📌 Tópicos
- `POST /topicos` → Registrar un nuevo tópico
- `GET /topicos` → Listar todos los tópicos
- `GET /topicos/{id}` → Obtener un tópico por ID
- `PUT /topicos/{id}` → Actualizar un tópico
- `DELETE /topicos/{id}` → Eliminar un tópico

---

## 🛠️ Ejemplo de uso con cURL

### Crear un tópico
```bash
curl -X POST http://localhost:8080/topicos -H "Content-Type: application/json" -H "Authorization: Bearer <token>" -d '{
	"titulo" : "Nuevo Topico",
	"mensaje" : "Nuevo mensaje",
	"autorId" : 1,
	"curso" : "Curso"
}'
```

### Listar tópicos
```bash
curl -X GET http://localhost:8080/topicos -H "Authorization: Bearer <token>"
```

---
### Reglas de negocio

- No se permiten tópicos duplicados (mismo título y mismo mensaje).
- En la creación (`POST /topicos`) se debe enviar el `id` del autor (usuario existente).
- En la actualización (`PUT /topicos/{id}`):
  - **No se puede cambiar el autor del tópico**.
  - Solo se pueden modificar el título, mensaje y curso.
- La fecha de creación (`fecha_creacion`) no se puede modificar.
- Si se intenta actualizar o eliminar un tópico inexistente, se retorna `404 Not Found`.

---

## 📜 Licencia

Este proyecto es parte de los desafíos de **Alura Latam + Oracle Next Education (ONE)**.  
Uso libre para fines educativos.

![Java](https://img.shields.io/badge/Java-21-red)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3-green)
![MySQL](https://img.shields.io/badge/MySQL-8-blue)
![JWT](https://img.shields.io/badge/JWT-Security-orange)
