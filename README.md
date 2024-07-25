# Proyecto de Foro

Este proyecto es una implementación de un foro donde los usuarios pueden comentar en las entradas. La clase `Post` es  parte fundamental de esta implementación.

## Convenciones de Codificación

se a seguido las siguientes convenciones en la implementacion:

1. **Nombres Claros y Descriptivos:**
    - Nombres de clases, métodos y variables son claros y reflejan su propósito.
    - Ejemplo: La clase `Post` representa una publicación en el foro.

2. **Funciones con Responsabilidades Claras:**
    - Cada método y función tiene una única responsabilidad, lo que facilita su lectura y mantenimiento.
    - Ejemplo: Los métodos de acceso (`getters` y `setters`) siguen esta práctica.

## Prácticas de Codificación Legible

### Nombres

uso de nombres claros y descriptivos para la clase y variables, lo que facilita la comprensión del código.

### excepciones

un mejor manejo en las excepciones

## Buenas Prácticas en `ForoUser`

1. **Uso de Lombok para Reducir Código Boilerplate:**
   - El uso de `@Getter`, `@Setter`, `@Builder`, `@AllArgsConstructor`, y `@NoArgsConstructor` ayuda a reducir el código repetitivo, haciendo la clase más limpia y fácil de mantener.

2. **Definición Clara de Relaciones en la Base de Datos:**
   - Las anotaciones JPA como `@OneToOne`, `@ManyToMany`, `@JoinColumn`, y `@JoinTable` proporcionan una definición precisa y clara de las relaciones entre entidades, facilitando la comprensión de la estructura de la base de datos.

3. **Columnas Claras y Específicas:**
   - Las anotaciones `@Column` con atributos como `unique`, `nullable`, y `name` aseguran una especificación detallada del comportamiento esperado de las columnas en la base de datos.

**Ejemplo de Código:**

```java
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class ForoUser {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id; 

   @Column(unique = true, nullable = false)
   private String username;

   @Column(nullable = false)
   private String password;

   @OneToOne
   @JoinColumn(name = "id_person", referencedColumnName = "id", 
   foreignKey = @ForeignKey(name = "FK_person_user"), nullable = false)
   private Person person;

   @Column(name = "is_enabled")
   private boolean isEnabled;

   @Column(name = "account_no_expired")
   private boolean accountNoExpired;

   @Column(name = "account_no_locked")
   private boolean accountNoLocked;

   @Column(name = "credential_no_expired")
   private boolean credentialNoExpired;

   @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
   @Builder.Default
   private Set<Role> roles = new HashSet<>();
}
```

## Buenas Prácticas en `UserService.java`

### Buenas Prácticas de Clean Code

1. **Inyección de Dependencias a través del Constructor:**
   - **Descripción:** La inyección de dependencias a través del constructor facilita la prueba unitaria de la clase y asegura que todas las dependencias necesarias estén presentes al momento de la creación del objeto.
     ```java
     @Autowired
     public UserService(IPersonService personService, UserRepositoryImp userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
       this.userRepository = userRepository;
       this.personService = personService;
       this.passwordEncoder = passwordEncoder;
       this.jwtUtil = jwtUtil;
     }
     ```

2. **Nombres Claros y Descriptivos:**
   - **Descripción:** Los nombres de los métodos y variables son claros y descriptivos, lo que mejora la legibilidad del código y facilita su comprensión.
     ```java
     public ForoUser registerUser(SignupFieldsDTO fields)
     public ForoUser getUserbyId(Long id)
     public String loginUser(LoginRequestDTO loginRequest)
     ```

3. **Uso de DTOs para la Transferencia de Datos:**
   - **Descripción:** Utilizar objetos de transferencia de datos (DTOs) para encapsular datos de entrada y salida ayuda a mantener el código modular y enfocado.
     ```java
     public ForoUser registerUser(SignupFieldsDTO fields)
     ```

4. **Métodos Pequeños y Enfocados:**
   - **Descripción:** Los métodos son pequeños y están enfocados en realizar una única tarea. Esto mejora la legibilidad y facilita la comprensión del propósito de cada método.
     ```java
     public Authentication authenticate(String username, String password)
     public String loginUser(LoginRequestDTO loginRequest)
     ```

5. **Manejo Claro de Excepciones:**
   - **Descripción:** El manejo de excepciones, proporcionando mensajes útiles para la depuración y el diagnóstico.
     ```java
     throw new RuntimeException("Error al crear usuario");
     ```

6. **Uso de `@Transactional` para Manejo de Transacciones:**
   - **Descripción:** La anotación `@Transactional` asegura que las operaciones que modifican datos se realicen dentro de una transacción, ayudando a mantener la integridad de los datos.
   - **Ejemplo:**
     ```java
     @Override
     @Transactional
     public ForoUser registerUser(SignupFieldsDTO fields)
     ```

7. **Uso del Patrón `Builder` para la Creación de Objetos:**
   - **Descripción:** El patrón `Builder` facilita la creación de objetos complejos de manera clara y legible, evitando constructores con múltiples parámetros.
   - **Ejemplo:**
     ```java
     ForoUser userCreated = ForoUser.builder()
         .username(fields.username())
         .password(passwordEncoder.encode(fields.password()))
         .person(personCreated)
         .roles(Set.of(userRole, adminRole))
         .build();
     ```


## -----------------------------------------------------------------------------------------------------------

## principios SOLID

1. **inyectar dependencias**

en `PostService` se usa en PostService  @Autowired para, la inyección de dependencias de Spring. 
implicando que PostService no crea instancias directamente, sino que depende de abstracciones gestionadas por el contenedor de Spring. 

2. **Principio de Sustitución de Liskov**

el uso de la inyección de dependencias y el hecho de que PostService pueda utilizar cualquier implementación de `EntryService`, `PostRepositoryImp` y `UserService`. permitiendo que PostService funcione correctamente independientemente de las implementaciones concretas.

El principio de sustitución de Liskov se cumple porque cualquier instancia de una subclase de `Role` puede ser utilizada en lugar de una instancia de `Role` sin alterar el comportamiento esperado del sistema.

```java
public void printRoleName(Role role) {
  System.out.println(role.getName());
}

public void exampleUsage() {
  Role adminRole = new AdminRole();
  printRoleName(adminRole); // AdminRole puede ser usado aquí
}
```

3. **Principio de Responsabilidad Única**

`Post` está principalmente enfocado en la gestión de posts, específicamente en su creación y recuperación. Aunque se delegan tareas como la creación de una entrada y la obtención de un usuario, estas operaciones son parte integral de la creación de un post.

La clase `Person` solo se encarga de representar los datos de una persona. No tiene ninguna lógica adicional, ni responsabilidades más allá de almacenar la información de una persona.

```java
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private LocalDate dateOfBirthDay;

}
```


4. **Principio de Encapsulamiento**

Los atributos de la clase `Post` son privados y se accede a ellos a través de métodos públicos (getters y setters). Esto asegura que los datos estén encapsulados y solo se puedan modificar de forma controlada, lo que protege la integridad del objeto.

## 5. Interface Segregation Principle (ISP)

`IUserService` cumple con el principio de segregación de interfaces ya que define solo los métodos necesarios para el servicio de usuario. No obliga a implementar métodos innecesarios para las clases que lo implementan.

```java
public interface IUserService extends UserDetailsService {
  ForoUser registerUser(SignupFieldsDTO signupFields);
  ForoUser getUserbyId(Long id);
  String loginUser(LoginRequestDTO loginRequest);
  Authentication authenticate(String username, String password);
}
```




