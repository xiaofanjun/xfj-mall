FROM java:8
ADD search-provider-1.0-SNAPSHOT.jar search-service.jar
ENTRYPOINT ["java","-jar","/search-service.jar","--spring.profiles.active=dev"]