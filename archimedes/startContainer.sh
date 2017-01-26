docker run -e "SPRING_PROFILES_ACTIVE=archimedes1" -p 8761:8761 \
  --name archimedes1 --add-host archimedes1:192.168.99.100 --add-host archimedes2:192.168.99.100 --add-host my-rabbit:192.168.99.100 \
  -d storyteller/archimedes 
docker run -e "SPRING_PROFILES_ACTIVE=archimedes2" -p 8762:8762 \
  --name archimedes2 --add-host archimedes1:192.168.99.100 --add-host archimedes2:192.168.99.100 --add-host my-rabbit:192.168.99.100 -d storyteller/archimedes