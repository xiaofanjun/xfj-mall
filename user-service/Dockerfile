FROM java:8
ADD user-provider-0.0.1-SNAPSHOT.jar user-service.jar
ENTRYPOINT ["java","-jar","/user-service.jar","--spring.profiles.active=test"]