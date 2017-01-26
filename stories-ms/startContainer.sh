docker run -e "SPRING_PROFILES_ACTIVE=stories1" -p 9988:9988 --name stories1 --add-host archimedes1:192.168.99.100 --add-host archimedes2:192.168.99.100 --add-host config-server:192.168.99.100 --add-host my-rabbit:192.168.99.100 --add-host gatekeeper:192.168.99.100 --add-host stories-microservice:192.168.99.100 --add-host images-microservice:192.168.99.100 --add-host storyteller-api:192.168.99.100 --add-host hystrix-dashboard:192.168.99.100 -d storyteller/stories-ms
docker run -e "SPRING_PROFILES_ACTIVE=stories2" -p 9987:9987 --name stories2 --add-host archimedes1:192.168.99.100 --add-host archimedes2:192.168.99.100 --add-host config-server:192.168.99.100 --add-host my-rabbit:192.168.99.100 --add-host gatekeeper:192.168.99.100 --add-host stories-microservice:192.168.99.100 --add-host images-microservice:192.168.99.100 --add-host storyteller-api:192.168.99.100 --add-host hystrix-dashboard:192.168.99.100 -d storyteller/stories-ms