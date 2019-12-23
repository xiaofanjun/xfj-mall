FROM java:8
ADD comment-provider-0.0.1-SNAPSHOT.jar comment-service.jar
ENTRYPOINT ["java","-jar","/comment-service.jar","--spring.profiles.active=dev"]