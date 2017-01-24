mvn clean package docker:build -DskipTests
docker run -p 5672:5672 \
  --hostname my-rabbit --name my-rabbit --add-host archimedes1:192.168.99.100 --add-host archimedes2:192.168.99.100 \
  -d rabbitmq:3
docker run -p 8888:8888 --name config-server \
  --add-host archimedes1:192.168.99.100 --add-host archimedes2:192.168.99.100 --add-host my-rabbit:192.168.99.100 \
  -d storyteller/config-server