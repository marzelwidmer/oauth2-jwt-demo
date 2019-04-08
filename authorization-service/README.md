## Creating Self Signed Certificate
Follow this guide to [creating self signed certificate](https://dzone.com/articles/creating-self-signed-certificate)
or [spring-boot-ssl-https-example](https://howtodoinjava.com/spring-boot/spring-boot-ssl-https-example/) 

```bash
keytool -genkey -alias selfsignedJwt -keyalg RSA -keysize 2048 -validity 700 -keypass changeit -storepass changeit -keystore jwt.jks
```
Let’s understand above command

- genkey – is the keytool command to generate the certificate, actually keytool is a multipurpose and robust tool which has several options
- alias selfsigned_localhost_sslserver – indicates the alias of the certificate, which is used by SSL/TLS layer
- keyalg RSA -keysize 2048 -validity 700 – are self descriptive parameters indicating the crypto algorithm, keysize and certificate validity.
- keypass changeit -storepass changeit – are the passwords of our truststore and keystore
- keystore jwt.jks – is the actual keystore where the certificate and public/private key will be stored. Here we are using JKS fromat – Java Key Store, there are other formats as well for keystore.


To view what is inside this keystore we can again use the keytool -list command as bellow.
```bash
keytool -list -keystore jwt.jks
```
