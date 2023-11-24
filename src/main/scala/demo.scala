import com.typesafe.config.{Config, ConfigFactory}
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions.{col, expr, mean, monotonically_increasing_id, row_number, when}
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession, functions}

import java.io.File

object demo extends App {

  // Create a Spark session
  val spark = SparkSession.builder
    .appName("SparkSQLServerExample")
    .master("local[*]") // Set the master URL for local mode
    .getOrCreate()
  spark.sparkContext.setLogLevel("WARN")

  // Explicitly specify the path to the .env file
  val configFile = new File(".env")

  // Load the configuration file
  val config: Config = ConfigFactory.parseFile(configFile)

  // Access the configurations using the correct key
  val sqlServerName: String = config.getString("SQL_SERVER_NAME")
  val tableName: String = config.getString("SQL_SERVER_TABLE")
  val jdbcDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver"

  val url: String = config.getString("url")
  val user: String = config.getString("user")
  val password: String = config.getString("password")
  val role: String = config.getString("role")
  val warehouse: String = config.getString("warehouse")
  val database: String = config.getString("database")
  val schema: String = config.getString("schema")

  val configMap = Map(
    "sfURL" -> url,
    "sfUser" -> user,
    "sfPassword" -> password,
    "ROLE" -> role,
    "sfWarehouse" -> warehouse,
    "sfDatabase" -> database,
    "sfSchema" -> schema
  )

  var df: DataFrame = spark.read
    .format("jdbc")
    .option("url", sqlServerName)
    .option("dbtable", tableName)
    .option("driver", jdbcDriver)
    .load()
  df.show(5)

//  println("display null values in each column")
//  var nullCounts = df.columns.map(c => functions.sum(when(col(c).isNull || col(c).isNaN, 1).otherwise(0)).alias(c))
//  df.agg(nullCounts.head, nullCounts.tail: _*).show()

  // Fill half of the null values with mean and the other half with median
  val meanTotalBedrooms = df.select(mean(df("total_bedrooms"))).collect()(0).getDouble(0)
  val medianTotalBedrooms = df.select(expr("percentile_approx(total_bedrooms, 0.5)").cast(IntegerType)).collect()(0).getInt(0)
  val nullCount = df.filter(df("total_bedrooms").isNull).count()
  val halfNullCount = nullCount / 2

  df = df.withColumn("total_bedrooms", when(col("total_bedrooms").isNull,
    when(row_number().over(Window.orderBy(monotonically_increasing_id())) <= halfNullCount, meanTotalBedrooms)
      .otherwise(medianTotalBedrooms)
  ).otherwise(col("total_bedrooms")))
  df.show(5)

  val newdf = df.na.drop()

  newdf.write
      .format("net.snowflake.spark.snowflake")
      .options(configMap)
      .option("dbtable", "housing")
      .mode(SaveMode.Append)
      .save()

}
