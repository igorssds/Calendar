# spring-api-starter-template
Spring API starter template for new projects, it should have examples for the most common project scenarios.

The project is using Spring Boot 2.1.2 with Spring JPA, Spring Security, Swagger, Eureka Client (disabled by default), Feign, MapStruct and PostgreSQL. The authentication is made with JWT Token, with grant_type password.

# Basics
## Project Structure
The structure is following the most common used application layers convention.
- Controllers should have as few code as possible, validate the request values and send all the information needed to the Services layer
- Services should contain all the application businness logic and orchestrate database transactions and the Repository calls
- Repositories are meant only for database calls
(Database scripts for testing are localized in the **resource/scripts** folder)

**main/java**
- **config:** Application custom configurations classes
	- **DataConfig:** Database configuration
	- **SecurityConfig:** Security and cors configuration
	- **SwaggerConfig:** Swagger Configuration
	- **TokenMethodSecurityConfig:** Custom security configuration handler. Needed for custom PreAuthorize methods.
- **controller:** API Controllers
- **data:** All database related classes (minus entities). Already have some repositories and converters.
- **domain:** Domain entities
- **dto:** DTOs and POCOs
- **exception:** Custom exceptions
- **filter:** Request and response filters
- **handler:** Application handlers
- **properties:** Classes for getting the properties values saved on the resources/application.yml file
- **mapper:** Classes containing rulers for passing values from Entities to DTOs and the other way around
- **security:** Classes needed for custom PreAuthorize methods.
	- **TokenMethodSecurityExpressionRoot:** Class where all the custom PreAuthorize methods should be saved
- **service:** Businness logic and others services
	- **api:** Service wrappers for api calls, not exposing the library to the businness logic services. (In this case, the used lib is Feign)
		- **client:** API calls mapping
		- **model:** Request and response models for the API calls
- **util:** Utils and helpers classes

## Remarks
- Everything in English
- Create **ALL** entity relations as ```FetchType.LAZY```. Use Hibernate's ```join fetch``` if the relation is needed.
- Configurations should be put in the application.yml file. If needed, create files for each environment overwriting the 'base' configuration file.

# TODO
- [x] Basic structure, database access and security configuration
- [x] JWT Token generation and validation
- [x] Refresh token generation
- [x] Custom PreAuthorize methods
- [x] Query database with custom class as result
- [x] Third-party RestAPI call
- [x] Querying without JPA
- [x] StoredProcedure call
- [ ] GraphQL
- [ ] Send e-mails
- [ ] Cache
- [ ] Unit Testing
- [x] Model Mapping
- [x] Wildfly deploy package
- [x] Sonar
- [x] Generate text file with positional data
- [ ] Firewall???