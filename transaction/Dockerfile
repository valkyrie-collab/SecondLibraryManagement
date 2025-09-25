FROM openjdk:26-oraclelinux9
LABEL author="valkyrie"
ADD target/transaction.jar transaction.jar
ENTRYPOINT [ "java", "-jar", "/transaction.jar" ]