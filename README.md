#Docker
##build image mysql
1. docker network create rentify (Create docker network if not yet created)
2. docker build -t rentify_mysql_image -f Dockerfile.mysql . 
3. docker run -d --name rentify_mysql_container --network=rentify -p 3306:3306 rentify_mysql_image 
4. docker start rentify_mysql_container 
5. docker exec -it rentify_mysql_container mysql -uroot -p 
6. Note: password = default1111

##build image java
1. docker network create rentify (Create docker network if not yet created)
2. docker build -t booking_microservice -f Dockerfile.booking .
3. docker rm -f booking_microservice || true && docker run -d -e DOCKER_CONTAINER=true -e DB_IP=rentify_mysql_container:3306 -e DB_DB=rentify -e DB_USERNAME=root -e DB_PASSWORD=default1111 --name booking_microservice --network=rentify -p 8080:8080 booking_microservice

##run program in local without build in docker
mvn -Dspring-boot.run.jvmArguments="-Ddb.ip=<db ip> -Ddb.db=<db> -Ddb.username=<db username> -Ddb.password=<db password>"

##run in jar file
java -Ddb.ip=<db ip> -Ddb.db=<db> -Ddb.username=<db username> -Ddb.password=<db password> -jar Team5_ASS_booking-1.0.jar

##integration test
mvn clean verify -Ddb.ip=<db ip> -Ddb.db=<db> -Ddb.username=<db username> -Ddb.password=<db password>

##integration test
mvn clean install -Ddb.ip=<db ip> -Ddb.db=<db> -Ddb.username=<db username> -Ddb.password=<db password>
