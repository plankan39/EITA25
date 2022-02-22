#!/bin/bash
user="doc1"
userkeystore="doc1keystore"
keytool -keystore $userkeystore -genkeypair -alias $user -keyalg RSA
keytool -certreq -alias $user -keystore $userkeystore -file $user.csr
openssl x509 -req -in $user.csr -CA ../ca.cert -CAkey ../ca-key.key -CAcreateserial -out $user.cert
keytool -importcert -file ../ca.cert -keystore $userkeystore -alias CA
keytool -importcert -file $user.cert -keystore $userkeystore -alias $user
rm $user.cert $user.csr
