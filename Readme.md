## Tools
Following are the tools needed to build and run the project:
- Maven
- Docker

## How to run
While being at the project root directory, execute as follows:

    $ mvn clean package
    $ docker build -t springio/gs-spring-boot-docker .
    $ docker run -p 8080:8080 -t springio/gs-spring-boot-docker
    
You can use `ctrl+c` to stop the container. 

## Sample Request
One can use postman to make the following request:

    http://localhost:8080/product?type=subscription&max_price=1000&city=Stockholm

Or can use curl from command line (given that curl is available in your terminal):
    
    $ curl -i http://localhost:8080/product?type=subscription&max_price=1000&city=Stockholm
    
## Test
To run the tests:
    
    $ mvn test
