FROM openjdk:26-oraclelinux9
LABEL author="valkyrie"
ADD target/entity.jar entity.jar
ENTRYPOINT [ "java", "-jar", "/entity.jar" ]