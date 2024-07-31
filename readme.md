## Estilos de programacion usados
### _1.- Error/Exception Handling_
Añadimos un manejador de errores global el cual se encargara de capturarlos.
```java
@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(CreationException.class)
  public ResponseEntity<ErrorDetails> handleCreationException (CreationException ex, WebRequest request) {
    ErrorDetails errorDetails = ErrorDetails.builder().timeStamp(new Date()).message(ex.getMessage()).details(request.getDescription(false)).build();
    return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<ErrorDetails> handleUnauthorized (UnauthorizedException ex, WebRequest request) {
    ErrorDetails errorDetails = ErrorDetails.builder().timeStamp(new Date()).message(ex.getMessage()).details(request.getDescription(false)).build();
    return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
  }
}
```
### Tipos de excepciones
#### CreationException
```java
public class CreationException extends RuntimeException{
  public CreationException (String message) {
    super(message);
  }
}
```
#### UnauthorizedException
```java
public class UnauthorizedException extends RuntimeException{
  public UnauthorizedException (String message) {
    super(message);
  }
}

```
### 2.- Pipeline Style
#### class JwlUtil ( obtener los roles del usuario )
```java
  public String generateToken (Authentication authentication){
    Algorithm algorithm = Algorithm.HMAC256(privateKey);

    String username = authentication.getPrincipal().toString();

    String authorities = authentication.getAuthorities()
                        .stream().map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(","));

    return JWT.create()
                      .withIssuer(this.userGenerator)
                      .withSubject(username)
                      .withClaim("authorities", authorities)
                      .withIssuedAt(new Date())
                      .withExpiresAt(new Date(System.currentTimeMillis() + 18000000))
                      .withJWTId(UUID.randomUUID().toString())
                      .withNotBefore(new Date(System.currentTimeMillis()))
                      .sign(algorithm);
  }
```
### 3.-Persistent Tables Style
#### Implementado para la tabla user
```java
@Table(name = "users")
public class ForoUser {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id; 

   @Column(unique = true)
   private String username;

   @JsonIgnore
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

  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinTable(name = "user_roles",joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

}
```
