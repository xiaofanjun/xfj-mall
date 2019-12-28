FROM java:8
ADD order-provider-0.0.1-SNAPSHOT.jar order-service.jar
ENTRYPOINT ["java","-jar","/order-service.jar","--spring.profiles.active=test"]