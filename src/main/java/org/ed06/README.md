# Hotel Management System

Sistema de gestión de hotel que permite administrar habitaciones, clientes y reservas.

## Generación de la Documentación

Para generar la documentación HTML del proyecto, sigue estos pasos:

1. Asegúrate de tener Maven instalado en tu sistema.
2. Abre una terminal en el directorio raíz del proyecto.
3. Ejecuta el siguiente comando:

```bash
mvn javadoc:javadoc
```

La documentación se generará en el directorio `target/site/apidocs/`.

## Estructura de la Documentación

La documentación generada incluye:

- **Clases del Modelo**:
  - `Hotel`: Gestiona las operaciones principales del hotel
  - `Cliente`: Representa a los clientes del hotel
  - `Habitacion`: Representa las habitaciones del hotel
  - `Reserva`: Gestiona las reservas de habitaciones

- **Clases de la Aplicación**:
  - `Main`: Punto de entrada de la aplicación
  - `HotelController`: Controlador principal que coordina las operaciones

## Acceso a la Documentación

Una vez generada, puedes acceder a la documentación abriendo el archivo `target/site/apidocs/index.html` en tu navegador web.

## Características de la Documentación

- Documentación completa de todas las clases y métodos
- Incluye ejemplos de uso
- Documentación de clases privadas
- Navegación intuitiva
- Búsqueda de clases y métodos 