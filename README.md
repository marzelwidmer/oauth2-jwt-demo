# OAuth2-JWT Authorization- Resource Server

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

