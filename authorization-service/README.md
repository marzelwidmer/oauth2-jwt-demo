## curl
```bash
curl -u clientId:secret -X POST localhost:9000/oauth/token\?grant_type=password\&username=user\&password=pass
```

## httpie
```bash
$ http POST :9000/oauth/token grant_type==password username==user password==pass -a clientId:secret -v
```

## Test Token
```bash
http :9000/users/me "Authorization: Bearer $token"
```