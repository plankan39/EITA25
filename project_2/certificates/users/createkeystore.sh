#!/bin/bash
user="pat1"
password="password"

keytool -keystore $user -dname "CN=$user, L=Lund, S=Skåne, C=SV" -genkeypair -alias $user -keyalg RSA -keypass $password -storepass $password
keytool -certreq -alias $user -keystore $user -file $user.csr -storepass $password -keypass $password
openssl x509 -req -in $user.csr -CA ../ca.cert -CAkey ../ca-key.key -CAcreateserial -out $user.cert
keytool -importcert -file ../ca.cert -keystore $user -alias CA -storepass $password
keytool -importcert -file $user.cert -keystore $user -alias $user -storepass $password
keytool -list -v -keystore $user -storepass $password
rm $user.cert $user.csr
