docker run -p 9966:9966 --name gatekeeper --add-host archimedes1:192.168.99.100 --add-host archimedes2:192.168.99.100 --add-host config-server:192.168.99.100 --add-host my-rabbit:192.168.99.100 --add-host gatekeeper:192.168.99.100 --add-host stories-microservice:192.168.99.100 --add-host images-microservice:192.168.99.100 --add-host storyteller-api:192.168.99.100 --add-host hystrix-dashboard:192.168.99.100 -d storyteller/gatekeeper