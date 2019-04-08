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
http :8080/me "Authorization: Bearer $token"
```

```bash
{
    "authenticated": true,
    "authorities": [
        {
            "authority": "ROLE_USER"
        }
    ],
    "clientOnly": false,
    "credentials": "",
    "details": {
        "decodedDetails": null,
        "remoteAddress": "0:0:0:0:0:0:0:1",
        "sessionId": null,
        "tokenType": "Bearer",
        "tokenValue": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NTQ3Mzg4NDksInVzZXJfbmFtZSI6InVzZXIiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiMGM0N2M2OGItZjFkNC00MTFhLWJiODQtNDViMWY1ZjBhZmUwIiwiY2xpZW50X2lkIjoiY2xpZW50SWQiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXX0.C_clgTqQeDGvq1n7mMj2f5Ctl30S6JbeCFD_Uf_icJZO1J9C6yJg0B3cez3JE3n5K1dfxnzr7FAgd98HPp6OkjbgQ7GvWvhULM2tRy3Tt9bNO4hV3xV5WcWiwBKK8Coha0LZ1NaFgvFb22Ky1H4MUkLmkboiU9G30P0hKUFEBdv1tB9GbPH6Mjt3ue5VLhfhDdiRwk9hn_-lVUPT0o1uWyrQpYIsg_BVvjnJ04LVZOPg4PYiusxEHxFRe79uplOQdTLort96zTEpOQJEktaJbzj2S3wgGTayFAFIx07dqKO-uHadjaLtlKYMdnwbyGKOeKLsJo1Emq4S5eN5ozVXIA"
    },
    "name": "user",
    "oauth2Request": {
        "approved": true,
        "authorities": [],
        "clientId": "clientId",
        "extensions": {},
        "grantType": null,
        "redirectUri": null,
        "refresh": false,
        "refreshTokenRequest": null,
        "requestParameters": {
            "client_id": "clientId"
        },
        "resourceIds": [],
        "responseTypes": [],
        "scope": [
            "read",
            "write"
        ]
    },
    "principal": "user",
    "userAuthentication": {
        "authenticated": true,
        "authorities": [
            {
                "authority": "ROLE_USER"
            }
        ],
        "credentials": "N/A",
        "details": null,
        "name": "user",
        "principal": "user"
    }
}
```

## Create Guest User Token
```bash
$ http POST :9000/oauth/token grant_type==password username==guest password==pass -a clientId:secret -v
```
Call again API with the _guest_ user
```bash
http :8080/users/me "Authorization: Bearer $token"
```
```bash
HTTP/1.1 403
Cache-Control: no-store
Content-Type: application/json;charset=UTF-8
Date: Mon, 08 Apr 2019 15:54:37 GMT
Pragma: no-cache
Transfer-Encoding: chunked
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
X-XSS-Protection: 1; mode=block

{
    "error": "access_denied",
    "error_description": "Access is denied"
}
```
