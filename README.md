# OAuth2-JWT Authorization- Resource Server

![#1589F0](https://placehold.it/15/1589F0/000000?text=+) `NOTE : Default Spring profile will start H2 Embedded Database`

## Spring CLI

### Start H2 Database
```bash
$ spring cloud h2
```

### Login 
Login URL : http://localhost:9095

![h2-login](../master/images/h2-login.png)

```bash
JDBC URL:: jdbc:h2:tcp://localhost:9096/mem:test
User Name: sa
Password: 
```
### Start Application 
Start _AuthorizationService_ with Spring profile _spring-cli_

```bash
$ java -jar -Dspring.profiles.active=spring-cli \
    authorization-service/target/authorization-service-0.0.1-SNAPSHOT.jar
```
 
## curl
```bash
curl -u clientId:secret -X POST localhost:9000/oauth/token\?grant_type=password\&username=user\&password=pass
```

## httpie
```bash
$ http POST :9000/oauth/token grant_type==password username==user password==pass -a clientId:secret -v
```

## Test token
```bash
http :9000/users/me "Authorization: Bearer $token"
```

