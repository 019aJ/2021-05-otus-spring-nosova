FROM openjdk:11
COPY target/emergency-monitoring-system-0.0.1-SNAPSHOT.jar emergency-monitoring-system.jar
#ENTRYPOINT ["java","-jar","emergency-monitoring-system.jar"]
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /emergency-monitoring-system.jar"]
