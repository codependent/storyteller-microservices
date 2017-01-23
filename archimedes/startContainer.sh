mvn clean package docker:build -DskipTests
docker run -e "SPRING_PROFILES_ACTIVE=archimedes1" -p 8761:8761 -d storyteller/archimedes 
docker run -e "SPRING_PROFILES_ACTIVE=archimedes2" -p 8762:8762 -d storyteller/archimedes

