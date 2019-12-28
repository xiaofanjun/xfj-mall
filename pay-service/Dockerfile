FROM java:8
ADD pay-provider-0.0.1-SNAPSHOT.jar pay-service.jar
ENTRYPOINT ["java","-jar","/pay-service.jar","--spring.profiles.active=test"]