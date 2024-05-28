# Spring Boot Application with MySQL using Docker Compose

This project demonstrates how to run a Spring Boot application with MySQL using Docker Compose.

## Prerequisites

- Docker
- Docker Compose

## Getting Started

1. Clone the repository:

   ```sh
   git clone https://github.com/hakimamarullah/vascomm-springboot
   ```

2. Navigate to the project directory:

   ```sh
   cd vascomm-springboot
   ```

3. Start MySQL container:

   ```sh
   docker-compose up --build -d
   ```

4. Run application (skip this step if the container already running from previous step):

   ```sh
   ./mvnw spring-boot:run
   ```
5. Access the Spring Boot application at [http://localhost:8080](http://localhost:8080).

## Testing the Application

1. Open your web browser and navigate to [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui.html) to access the Swagger UI.

2. Use the Swagger UI to test the various endpoints exposed by the Spring Boot application.

## Stopping the Application

1. [non-detached] To stop the application and remove the containers, press `Ctrl + C` in the terminal where `docker-compose` is running.
2. If you use `-d` option then simple run this command to stop all containers `docker-compose down`


