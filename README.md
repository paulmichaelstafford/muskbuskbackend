# muskbuskbackend

This is the backend for the application MushBusk

# Run locally

    mvn clean spring-boot:run -Drun.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005" -Dspring.profiles.active=local

