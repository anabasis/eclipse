# README

```bash
export CP=.:amqp-client-5.7.1.jar:slf4j-api-1.7.26.jar:slf4j-simple-1.7.26.jar
java -cp $CP ReceiveLogsTopic "#"
java -cp $CP ReceiveLogsTopic "kern.*"
java -cp $CP ReceiveLogsTopic "*.critical"
java -cp $CP ReceiveLogsTopic "kern.*" "*.critical"


export CP=.:amqp-client-5.7.1.jar:slf4j-api-1.7.26.jar:slf4j-simple-1.7.26.jar
java -cp $CP EmitLogTopic "kern.critical" "A critical kernel error"

```
