FROM java:8
ADD xfj-user-0.0.1-SNAPSHOT.jar mall-user.jar
ENTRYPOINT ["java","-jar","/mall-user.jar","--spring.profiles.active=test"]