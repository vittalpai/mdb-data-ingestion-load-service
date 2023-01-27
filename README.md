# Data Ingestion and Data Load service for MongoDB
Java SpringBoot application that provides an data ingestion and data load service for MongoDB. The service includes two REST endpoints: one for data ingestion (POST) and another for fetching data from MongoDB (GET). Additionally, the service includes JMeter scripts for benchmarking the ingestion and data load performance of MongoDB. Overall, utilizing jmeter in combination with this service allows for thorough and accurate measurement of the performance and capabilities of the MongoDB cluster.

This ingestion and load service for MongoDB, along with the provided JMeter scripts, can be a powerful tool for benchmarking and measuring the performance of MongoDB. It can help you to test the performance of MongoDB under varying loads and conditions, and can be especially useful for testing the service in a production-like environment in the cloud. Additionally, the service can be customized based on the needs of the user, as long as they have prerequisite knowledge of Java and using Jmeter scripts.

This service can be deployed on a public cloud of your choice, such as AWS or GCP, for benchmarking MongoDB performance in a production-like environment. The advantage of this approach is that it allows you to mimic customer environments and test the service under varying loads and conditions. Additionally, by running the service in the cloud, you can take advantage of the scalability and flexibility of cloud services to handle large data loads and handle sudden bursts of traffic.

## Prerequisites
- Java 8 or higher
- MongoDB Server
- JMeter
- Knowledge of Java and Jmeter is required to customize the service as per the needs


## Getting Started
- Clone the repository to your local machine
    ```
    git clone https://github.com/vittalpai/mdb-data-ingestion-load-service.git
    ```

- Update the MongoDB Connection String and also customize the data ingestion/inset part based on your needs in the following file.
    `DataService/src/main/java/com/example/demo/api/DataService.java`

- Build the project.
    ```
    cd DataService
    mvn clean install
    ```
- Run the following command to start the service, The service will be running on `http://localhost:8080`
    ```
    mvn spring-boot:run
    ```

## Using the Service
- To ingest data, invoke a POST request to `http://localhost:8080/data/{id}`
- To fetch data, invoke a GET request to `http://localhost:8080/data/{id}`

## Running the JMeter Scripts
Using the provided jmeter scripts for data ingestion and load generation in conjunction with the java springboot application for the ingestion and load service for MongoDB allows for efficient and comprehensive benchmarking and performance testing. 

- Open JMeter and import the `JmeterScripts/data-ingestion.jmx` or `JmeterScripts/data-load.jmx` file.
- Update the thread count, ramp-up time, and target endpoint as per your needs.
- Run the script to generate the load.
   ```
   jmeter -n -t data-load.jmx -l results.jtl
   ```
- Analyze the results using the generated `results.jtl` file.