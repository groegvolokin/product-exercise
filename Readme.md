## Tools
Following are the tools needed to build and run the project:
- Maven
- Docker

## How to run
Following are the commands that are needed to run from project root directory

    $ mvn clean package
    $ docker build -t springio/gs-spring-boot-docker .
    $ docker run -p 8080:8080 -t springio/gs-spring-boot-docker
    
After that one can test with sample requests. Example is given in the following section. After finish run following commands:
    
    $ docker ps
   
Now you have the container id. Run the following command with container id:
   
    $ docker stop container_id
    
In fact, you can use `ctrl+c` which will stop the container. 

## Sample Request
One can use postman to make the following request:

    http://localhost:8080/product?type=subscription&max_price=1000&city=Stockholm

Or can use curl from command line (given that curl is available in your terminal):
    
    $ curl -i http://localhost:8080/product?type=subscription&max_price=1000&city=Stockholm
    
## Test
For running test following is the command:
    
    $ mvn test

