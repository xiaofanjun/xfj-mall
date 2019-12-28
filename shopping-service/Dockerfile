FROM java:8
ADD shopping-provider-0.0.1-SNAPSHOT.jar shopping-service.jar
ENTRYPOINT ["java","-jar","/shopping-service.jar","--spring.profiles.active=test"]