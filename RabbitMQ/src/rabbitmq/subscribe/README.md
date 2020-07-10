# README

```bash
rabbitmqctl list_bindings

export CP=.:amqp-client-5.7.1.jar:slf4j-api-1.7.26.jar:slf4j-simple-1.7.26.jar
javac -cp $CP EmitLog.java ReceiveLogs.java

java -cp $CP ReceiveLogs > logs_from_rabbit.log

rabbitmqctl list_bindings
```
