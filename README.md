#### This manual describes the project build and deploy process..

Please go to project root directory to build the project with below command:
>mvn clean install

Please use below command in the project root directory to create docker image:
>mvn clean package -Dmaven.test.skip=true dockerfile:build -f newsletter-subscription

You should know that system port is 8080 and should be free.

To run newly created docker image, use below command:
>docker run  -p 8080:8080 --name newsletter -d newsletter-subscription

You can use below command to see the running status of container:
>docker ps -a

You can see the log file with below command:
>docker exec -it newsletter  /bin/sh
>tail -n 100 -f /tmp/logs/newsletter-subscription.log


There is a file in the project directory(./files/newletter.postman_collection.json) for postman. Import it to postman to test the service methods with postman.

The system will show the rest methods with below url. Although this(swagger) is for development mode, but I didn’t remove it because of demonstration purposes:
>	url : http://localhost:8080/swagger-ui.html#/

At last you can remove the container and image just with below command:
>name=$(docker ps -aqf "name=newsletter"); docker stop $name; docker rm $name; docker rmi newsletter-subscription;
