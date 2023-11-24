ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.18"

libraryDependencies += "com.typesafe" % "config" % "1.4.2"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "3.5.0",
  "org.apache.spark" %% "spark-sql" % "3.5.0",
)
// https://mvnrepository.com/artifact/com.microsoft.azure/spark-mssql-connector
libraryDependencies += "com.microsoft.azure" %% "spark-mssql-connector" % "1.3.0-BETA"

// https://mvnrepository.com/artifact/com.microsoft.sqlserver/mssql-jdbc
libraryDependencies += "com.microsoft.sqlserver" % "mssql-jdbc" % "12.4.2.jre11"

// https://mvnrepository.com/artifact/net.snowflake/spark-snowflake
libraryDependencies += "net.snowflake" %% "spark-snowflake" % "2.13.0-spark_3.4"

// https://mvnrepository.com/artifact/org.apache.spark/spark-streaming
libraryDependencies += "org.apache.spark" %% "spark-streaming" % "3.5.0"



lazy val root = (project in file("."))
  .settings(
    name := "spark_mine"
  )
