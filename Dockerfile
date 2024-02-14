FROM openjdk:17-oracle
ADD target/logpuller.jar logpuller.jar
EXPOSE 8080
CMD ["java", "-jar", "logpuller.jar"]
