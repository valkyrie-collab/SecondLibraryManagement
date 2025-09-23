FROM openjdk:26-oraclelinux9
LABEL author="valkyrie"
ADD target/book.jar book.jar
ENTRYPOINT [ "java", "-jar", "/book.jar" ]