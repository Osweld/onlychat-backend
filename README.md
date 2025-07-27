# OnlyChat Backend

Una aplicación de chat en tiempo real desarrollada con **Spring Boot** y **WebSocket** que permite comunicación instantánea entre usuarios.

## 🚀 Características

- **Chat en tiempo real** con WebSocket y STOMP
- **Autenticación JWT** segura
- **Gestión de usuarios** y roles
- **Mensajes no leídos** con contadores dinámicos
- **Notificaciones** de mensajes leídos
- **API RESTful** bien documentada
- **Base de datos PostgreSQL**
- **Envío de emails** con notificaciones

## 🛠️ Tecnologías

- **Java 21**
- **Spring Boot 3.x**
- **Spring Security** (JWT)
- **Spring Data JPA**
- **WebSocket + STOMP**
- **PostgreSQL**
- **Maven**
- **Lombok**

## 📋 Prerrequisitos

- Java 21+
- PostgreSQL 12+
- Maven 3.8+

## ⚙️ Configuración

### 1. Variables de Entorno

Crea un archivo `.env` en la raíz del proyecto:

```env
# Database Configuration
DB_HOST=localhost
DB_PORT=5432
DB_DATABASE=onlychat
DB_USER=username
DB_PASSWORD=password

# JWT Configuration
SECRET_KEY=Tu_llave_secreta

# Email Configuration
SMTP_HOST=smtp.gmail.com
SMTP_PORT=587
SMTP_USER=tu-email@gmail.com
SMTP_PASSWORD=tu-app-password

# Frontend URL
FRONTEND_URL=frontend
```

### 2. Base de Datos

Crea la base de datos en PostgreSQL:

```sql
CREATE DATABASE onlychat;
```

### 3. Ejecutar la aplicación

```bash
# Clonar el repositorio
git clone <repository-url>
cd onlychat-backend

# Instalar dependencias
mvn clean install

# Ejecutar la aplicación
mvn spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080`

## 📚 API Endpoints

### Autenticación
- `POST /api/v1/auth/login` - Iniciar sesión
- `POST /api/v1/auth/register` - Registrar usuario
- `POST /api/v1/auth/refresh` - Renovar token

### Chats
- `GET /api/v1/chat/user/{userId}` - Obtener chats del usuario
- `GET /api/v1/chat/{chatId}/messages` - Obtener mensajes del chat
- `POST /api/v1/chat/messages/mark-as-read/{chatId}` - Marcar mensajes como leídos

### WebSocket
- **Conectar**: `/ws`
- **Enviar mensaje**: `/app/chat.sendMessage`
- **Recibir mensajes**: `/topic/chat.{chatId}`
- **Notificaciones**: `/user/queue/messages`
- **Mensajes leídos**: `/user/queue/message-seen`

## 🏗️ Estructura del Proyecto

```
src/main/java/german/dev/onlychatbackend/
├── auth/                   # Autenticación y seguridad
│   ├── controller/
│   ├── service/
│   └── dto/
├── chat/                   # Funcionalidades de chat
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── entity/
│   ├── dto/
│   └── projection/
├── user/                   # Gestión de usuarios
│   ├── entity/
│   ├── repository/
│   └── service/
├── config/                 # Configuraciones
│   ├── WebSocketConfig.java
│   ├── SecurityConfig.java
│   └── JwtConfig.java
└── OnlychatBackendApplication.java
```

## 📊 Modelo de Base de Datos

### Entidades Principales

- **User**: Usuarios del sistema
- **Chat**: Conversaciones
- **Message**: Mensajes individuales
- **ChatUser**: Relación usuarios-chats
- **Role**: Roles de usuario

### Relaciones

- `User` ↔ `ChatUser` ↔ `Chat` (Muchos a muchos)
- `Chat` → `Message` (Uno a muchos)
- `User` → `Message` (Uno a muchos)

## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

## 👨‍💻 Autor

**German Reyes** - [@osweld](https://github.com/osweld)

---

⭐ ¡¡Dale una estrella si te ha gustado el proyecto!