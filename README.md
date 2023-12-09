# ETL Pipeline with Scala Spark

This project implements an Extract, Transform, Load (ETL) pipeline using Scala and Apache Spark. The pipeline is designed to read data from a Microsoft SQL Server database, perform data cleaning and imputation, and then load the processed data into Snowflake. The project consists of the following files: `demo.scala` for the main application, `build.sbt` for project dependencies, and a `.env` file template for sensitive information.

## Project Structure

### 1. `demo.scala`

This Scala script contains the main ETL pipeline implementation. It performs the following steps:

- Creates a Spark session.
- Loads configurations from a `.env` file using the Typesafe Config library.
- Reads data from a Microsoft SQL Server database into a Spark DataFrame.
- Cleans and imputes missing values in the DataFrame.
- Writes the processed data to Snowflake.

### 2. `build.sbt`

This build file specifies the project's dependencies and configurations. Notable dependencies include Apache Spark, Typesafe Config, the Spark SQL Server Connector, the Microsoft SQL Server JDBC driver, and the Spark Snowflake Connector.

## Usage

To run the ETL pipeline, follow these steps:

1. Ensure you have [Apache Spark](https://spark.apache.org/downloads.html) installed on your machine.
2. Create a `.env` file with the necessary configurations. Refer to the provided `.env` template.
3. Update the dependencies in `build.sbt` if needed.

   ```bash
   libraryDependencies += "com.typesafe" % "config" % "1.4.2"
   libraryDependencies ++= Seq(
     "org.apache.spark" %% "spark-core" % "3.5.0",
     "org.apache.spark" %% "spark-sql" % "3.5.0",
     "com.microsoft.azure" %% "spark-mssql-connector" % "1.3.0-BETA",
     "com.microsoft.sqlserver" % "mssql-jdbc" % "12.4.2.jre11",
     "net.snowflake" %% "spark-snowflake" % "2.13.0-spark_3.4",
     "org.apache.spark" %% "spark-streaming" % "3.5.0"
   )
   ```

4. Run the `demo.scala` script using Spark-submit or your preferred method.

   ```bash
   spark-submit --class demo --master local[*] path/to/demo.jar
   ```

## .env Template

Create a `.env` file with the following template and fill in the sensitive information:

```env
SQL_SERVER_NAME=jdbc:sqlserver://your-sql-server-url:1433
SQL_SERVER_TABLE=your-sql-server-table
url=snowflake-url
user=snowflake-username
password=snowflake-password
role=snowflake-role
warehouse=snowflake-warehouse
database=snowflake-database
schema=snowflake-schema
```

Make sure to replace placeholders with your actual connection details.

## Dependencies

- Apache Spark: [https://spark.apache.org/](https://spark.apache.org/)
- Typesafe Config: [https://github.com/lightbend/config](https://github.com/lightbend/config)
- Spark SQL Server Connector: [https://docs.microsoft.com/en-us/sql/connect/spark/connector/download-microsoft-sql-server-spark-connector](https://docs.microsoft.com/en-us/sql/connect/spark/connector/download-microsoft-sql-server-spark-connector)
- Microsoft SQL Server JDBC Driver: [https://docs.microsoft.com/en-us/sql/connect/jdbc/download-microsoft-jdbc-driver-for-sql-server](https://docs.microsoft.com/en-us/sql/connect/jdbc/download-microsoft-jdbc-driver-for-sql-server)
- Spark Snowflake Connector: [https://docs.snowflake.com/en/user-guide/spark-connector.html](https://docs.snowflake.com/en/user-guide/spark-connector.html)

Feel free to adapt and extend the code according to your specific requirements. If you encounter any issues or have questions, refer to the documentation of the used libraries or seek assistance from the respective communities.
