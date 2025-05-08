# Dynamic Calculator Test API

## Descripción
Desarrollo en Spring Boot de un API Rest para sumar dos numeros y aplicar un porcentaje (50% fijo) obtenido desde un servicio externo, implementado con WireMockServer. Adicionalmente se implementó uso de Cache en memoria con caffeine, con un tiempo de expiración de 30 minutos, despues de este tiempo ya no existe el resultado en Cache.

Para simular la falla del servicio externo se utilizó @Scheduled para que a las 10 minutos se detenga el WireMockServer.

Si el WireMockServer esta detenido y hay resultados en cache se mostraran.

También se agregó Open API para hacer pruebas con Swagger http://localhost:8080/swagger-ui/index.html#/.

Por último se agregó Dockerfile y un compose para ejecutar la base de datos y el servicio Rest en un contenedor.

En el código backend se utilizaron los principios SOLID, patrón repository, DTO y buenas prácticas de desarrollo.


## Como hacer la Prueba
1. Clone el proyecto git clone git@github.com:yadicksonvasquez/dynamic-calculator-api.git
2. Configure las credenciales para la base de datos en el archivo /task-manager-api/src/main/resources/application.yml.
3. Compile el proyecto: mvn clean, mvn package o mvn install
4. Ejecuté el comando: docker compose up
5. Puede hacer pruebas con Swagger o hacer pruebas con Postman del API con los siguientes endpoints:

Sumar <br>
URI: /api/v1/calculator/addNumbers <br>
Tipo: POST <br>
Request:<br>
{
  "number1": 0,
  "number2": 0
}

Ver Historico de requests es un servicio asincrono<br>
URI: /api/v1/calculator-history<br>
Tipo: GET <br>

## Autor

- Author - Yadickson Vasquez
 yadicksonvasquez@gmail.com


