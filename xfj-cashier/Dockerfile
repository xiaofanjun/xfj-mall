FROM java:8
ADD xfj-cashier-0.0.1-SNAPSHOT.jar cashier.jar
ENTRYPOINT ["java","-jar","/cashier.jar","--spring.profiles.active=test"]