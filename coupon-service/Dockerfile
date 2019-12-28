FROM java:8
ADD coupon-provider-1.0-SNAPSHOT.jar coupon-service.jar
ENTRYPOINT ["java","-jar","/coupon-service.jar","--spring.profiles.active=test"]