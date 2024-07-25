## README

### Prácticas Usadas en el Proyecto

Este repositorio contiene un proyecto Java para gestionar entradas de un blog (entries) y sus comentarios. A continuación, se detallan algunas de las prácticas y técnicas utilizadas en este proyecto.

### Estructura del Repositorio

El repositorio está organizado de la siguiente manera:

- **`com.app.domain.post`**: Contiene la clase `Entry`, que representa una entrada de blog.
- **`com.app.domain.user`**: Define la entidad `ForoUser` para los usuarios del blog.
- **`com.app.repositories.post`**: Contiene `EntryRepositoryImp`, que extiende `JpaRepository` para las operaciones de persistencia de las entradas.
- **`com.app.services.interfaces.post`**: Define la interfaz `IEntryService` para la lógica de negocio relacionada con las entradas.
- **`com.app.services.implementations.post`**: Implementa `IEntryService` en `EntryService`, proporcionando métodos para crear entradas y añadir comentarios.

### Principales Prácticas y Técnicas Utilizadas

1. **Spring Framework**:
   - Utilización de `@Autowired` para la inyección de dependencias en `EntryService`.
   - Anotación `@Service` para marcar `EntryService` como un servicio de Spring.
   - Uso de `@Transactional` para asegurar transacciones en métodos críticos de servicio.

2. **JPA (Java Persistence API)**:
   - Definición de entidades (`Entry` y `ForoUser`) usando anotaciones JPA como `@Entity`, `@Table`, `@Column`, `@Id`, y `@ManyToOne`.
   - Repositorio de datos (`EntryRepositoryImp`) que extiende `JpaRepository` para operaciones CRUD básicas.

3. **Gestión de Excepciones**:
   - Manejo de excepciones específicas como `NoSuchElementException` para operaciones de búsqueda y validaciones.

4. **Base de Datos**:
   - Uso de tipos de datos específicos (`TEXT`, `INT`) en las columnas de la tabla de entradas (`entry`) para optimización y claridad.

5. **Seguridad y Relaciones de Datos**:
   - Establecimiento de relaciones (`@ManyToOne`) entre `Entry` y `ForoUser` para asociar entradas con usuarios.

6. **Auditoría de Datos**:
   - Uso de campos `createdAt` y `lastUpdate` en `Entry` para registrar la fecha de creación y última actualización automáticamente.

### Configuración y Ejecución

Para ejecutar este proyecto, asegúrese de tener configurado un entorno de desarrollo Java con las dependencias necesarias (como Spring Boot y Spring Data JPA). Asegúrese también de configurar correctamente la conexión a la base de datos.

### Contribución

Si desea contribuir a este proyecto, puede realizar un fork del repositorio, implementar cambios y enviar un pull request. Asegúrese de seguir las convenciones de código y mantener la coherencia con el estilo existente.

### Contacto

Para cualquier duda o sugerencia, no dude en contactar al equipo de desarrollo a través de los issues de GitHub o directamente por correo electrónico.

---

Este README proporciona una visión general de alto nivel de las prácticas utilizadas y la estructura del proyecto, facilitando la comprensión y colaboración por parte de otros desarrolladores interesados en el proyecto.