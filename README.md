# 🚀 SGCM - Sistema de Gestión de Campañas de Marketing

Sistema web desarrollado para la gestión integral de campañas de marketing en la empresa **BDO Outsourcing S.A.C.**, permitiendo centralizar la información, gestionar tareas, asignar responsables y generar reportes.

---

## 📌 Descripción del Proyecto

El sistema SGCM permite optimizar la organización y control de campañas de marketing, reemplazando el uso de herramientas dispersas como Excel, correos y mensajería.

### 🎯 Problema

* Información desorganizada
* Falta de seguimiento de tareas
* Dificultad para medir resultados

### ✅ Solución

* Centralización de campañas
* Gestión de tareas tipo Kanban
* Asignación de responsables
* Reportes y auditoría

---

## 🏗️ Arquitectura del Sistema

El proyecto sigue una arquitectura basada en:

### ✅ MVC (Modelo - Vista - Controlador)

* **Modelos:** entidades del sistema (Usuario, Tarea, Campaña, etc.)
* **Vistas:** HTML + CSS + JavaScript
* **Controladores:** manejo de endpoints (REST API)

### ✅ DAO (Data Access Object)

Separación de acceso a datos mediante:

```text
Repositories/
├── Interface/
└── Implementation/
```

### ✅ SOLID

* Código limpio, modular y escalable
* Uso de interfaces en servicios

---

## 🛠️ Tecnologías Utilizadas

### 🔹 Backend

* Java
* Jakarta EE / JAX-RS
* JWT (Autenticación)
* JPA / JDBC
* PostgreSQL

### 🔹 Frontend

* HTML5
* CSS3 (Bootstrap 5)
* JavaScript

### 🔹 Librerías

* ✅ Apache Commons
* ✅ Apache POI (reportes Excel)
* ✅ Google Guava
* ✅ Jackson (JSON)
* ✅ Logback (logs)

---

## 🔐 Seguridad

El sistema incluye:

* Autenticación con JWT
* Encriptación de contraseñas
* Filtros de autorización (JwtFilter)
* Control de sesión (UserContext)

---

## 📂 Estructura del Proyecto

```text
SGCM/
├── Controllers/
├── Models/
├── DTOs/
├── Services/
├── Repositories/
├── Security/
├── Utils/
└── webapp/views/
```

---

## 🎨 Funcionalidades del Sistema

### ✅ Módulos implementados

* 🔐 Login de usuarios
* 📊 Dashboard
* 📢 Gestión de campañas
* ✅ Gestión de tareas (Kanban)
* 👥 Gestión de usuarios
* 🔔 Notificaciones
* 📊 Reportes
* 🧾 Auditoría

---

## 🧪 Pruebas

Se implementaron pruebas unitarias con JUnit:

```text
src/test/java/services/
├── UsuarioServiceTest
├── TareaServiceTest
├── CampaniaServiceTest
└── EmpresaServiceTest
```

---

## 📈 Control de Versiones

El proyecto utiliza:

* ✅ Git
* ✅ GitHub

Permite:

* Historial de cambios
* Gestión de versiones
* Control de desarrollo

---

## 📸 Capturas del Sistema

📌 (Agregar aquí imágenes reales de tu sistema)

* Login
* Dashboard
* Campañas
* Tareas
* Usuarios
* Auditoría

---

## 🚀 Instalación y Ejecución

### 🔧 Requisitos

* Java JDK 17+
* Maven
* PostgreSQL

### ▶️ Pasos

```bash
# Clonar repositorio
git clone https://github.com/TU-USUARIO/SGCM.git

# Entrar al proyecto
cd SGCM

# Compilar
mvn clean install

# Ejecutar
mvn jakartaee:run
```

---

## 📊 Base de Datos

* Motor: PostgreSQL
* Script: (agregar script SQL si tienes)

---

## 👨‍💻 Autor

**Alexis Medina Pérez**
Ingeniería de Sistemas e Informática

---

## 📚 Proyecto Académico

Curso: **Curso Integrador I - Sistemas Software**
Docente: **Juan Barahona Sánchez**
Año: **2026**

---

## ✅ Estado del Proyecto

✅ Completado (Versión Académica)

---

## 🔥 Mejoras Futuras

* Integración con redes sociales
* Dashboard analítico avanzado
* Migración a cloud (AWS / Azure)
* Implementación completa de TDD
* CI/CD con GitHub Actions

---

## 📌 Licencia

Proyecto académico — uso educativo.
