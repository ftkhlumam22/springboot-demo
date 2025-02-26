
## Description

This project consists of two Spring Boot microservices:

- **Backend-Service**: Acts as the primary interface for clients, exposing REST APIs to manage users and accounts. It communicates with the **Account-Service** via Apache Kafka messages.

- **Account-Service**: Listens to Kafka messages from the **Backend-Service** to perform operations such as creating users and accounts. It interacts with an Oracle Database to store and manage data, utilizing Flyway for database migrations.

### Installation

1. **Clone the Repository**

    ```bash
    git clone https://github.com/ftkhlumam22/springboot-demo.git
    cd springboot-demo
    ```

2. **Configure Application Properties**

    - Update `application.properties` for both services with your Oracle and Kafka configurations.

### Running the Services

1. **Start Oracle Database and Ensure Schema `BO_SERVICE_DATA` Exists**

2. **Start Apache Kafka and Create Necessary Topics**

    ```bash
    bin/kafka-topics.sh --create --topic account_requests --bootstrap-server your-kafka-server:9092 --partitions 1 --replication-factor 1
    ```

3. **Run Account-Service**

    ```bash
    cd account-service
    ./gradlew bootRun
    ```

4. **Run Backend-Service**

    ```bash
    cd backend-service
    ./gradlew bootRun
    ```
