
from pyspark.sql import SparkSession, dataframe
from pyspark.sql.functions import col, hour, split, to_date, concat, lit

from .ParameterConstants import data_file_path

# The Main Data Analysis Module using the PySpark Module.
# The Methods for Implementing the 3 use cases of the Project are implemented in this Class
class DataAnalysis:



    def __init__(self) -> None:
        self.__spark = SparkSession.builder.appName("Kafka-Spark-Stream").getOrCreate()
        self.__dataframe = self.__spark.read.csv(data_file_path,header=True)

        # The Kafka Cluster Stores floor and sensor numbers serially from zero.
        # For Better View Point Observation. We Would update the dataframe to have 
        # Sensor Ids and Floor Numbers from 1.

        self.__dataframe = self.__dataframe.withColumn("Floor_Id",(self.__dataframe["Floor_Id"]+1).cast(dataType="integer"))
        self.__dataframe = self.__dataframe.withColumn("Sensor_Id",(self.__dataframe["Sensor_Id"]+1).cast(dataType="integer"))

        # On Checking the Schema of Dataframe, we could observe that Temperature is being considered to be
        # of type String.
        # Casting Temperature Column from String to Double

        self.__dataframe = self.__dataframe.withColumn("Temperature",self.__dataframe["Temperature"].cast(dataType="double"))

        # For Ease of Processing, We Will parse Timestamp to the necessary datatype
        self.__dataframe = self.__dataframe.withColumn("Timestamp",self.__dataframe["Timestamp"].cast(dataType="Timestamp"))

    def get_spark_session(self):
        return self.__spark

    def get_hourly_avg_temperature(self):
        '''
            Grouping the Dataframe by breaking the timestamp into Date and Hour, Floor.
            Computing the Mean temperature post grouping.
            Merging the Hour and Date to Timestamp post grouping and Aggregation.
            Returning the dataframe post appropriate column renaming.
        '''

        dataframe_1hr = self.get_dataframe().groupBy(
            to_date("Timestamp").alias("Date"),
            hour("Timestamp").alias("Hour"),
            "Floor_Id").mean("Temperature")

        dataframe_1hr = dataframe_1hr.withColumn("Hour", dataframe_1hr["Hour"].cast("string"))
        dataframe_1hr = dataframe_1hr.withColumn("Date", dataframe_1hr["Date"].cast("string"))
        dataframe_1hr = dataframe_1hr.withColumn("Timestamp", concat("Date",lit(" "),"Hour"))
        dataframe_1hr = dataframe_1hr.withColumn("Timestamp",dataframe_1hr["Timestamp"].cast("timestamp"))
        dataframe_1hr = dataframe_1hr.withColumnRenamed("avg(Temperature)","Mean_Temperature")
        dataframe_1hr = dataframe_1hr.orderBy("Timestamp")

        return dataframe_1hr.select("Timestamp","Floor_Id","Mean_Temperature")
    
    def get_daily_max_temperature(self):
        '''
            Grouping the Dataframe by breaking the timestamp into Date, Floor.
            Computing the Maximum temperature post grouping.
            Ordering the Data Frame According to the Date
            Returning the dataframe post appropriate column renaming.
        '''

        dataframe_max = self.get_dataframe().groupBy(
            to_date("Timestamp").alias("Date"),
            "Floor_Id").max("Temperature")

        dataframe_max = dataframe_max.withColumnRenamed("max(Temperature)","Max_Temperature")
        dataframe_max = dataframe_max.orderBy("Date")
        return dataframe_max

    def get_daily_min_temperature(self):
        '''
            Grouping the Dataframe by breaking the timestamp into Date, Floor.
            Computing the Minimum temperature post grouping.
            Ordering the Data Frame According to the Date
            Returning the dataframe post appropriate column renaming.
        '''

        dataframe_min = self.get_dataframe().groupBy(
            to_date("Timestamp").alias("Date"),
            "Floor_Id").min("Temperature")
        dataframe_min = dataframe_min.withColumnRenamed("min(Temperature)","Min_Temperature")
        dataframe_min = dataframe_min.orderBy("Date")
        return dataframe_min

    def get_daily_avg_temperature_with_sensor(self):
        '''
            In order to group each sensor on each floor uniquely we merge the SensorId and FloorId Columns.
            Grouping the Dataframe by breaking the timestamp into Date, FloorId_SensorId.
            Computing the Mean temperature post grouping.
            Post the Aggregation, we split the merged floor and sensor columns.
            We Finally Sort the Dataframe by Date and Average Temperature.
            Returning the dataframe post appropriate column renaming.
        '''

        dataframe_daily = self.get_dataframe()
        dataframe_daily = dataframe_daily.withColumn("Floor_Id_Sensor_Id",
                concat("Floor_Id",lit(" "),"Sensor_Id"))
        
        dataframe_daily = dataframe_daily.groupBy(
            to_date("Timestamp").alias("Date"),
            "Floor_Id_Sensor_Id"
        ).mean("Temperature")

        dataframe_daily = dataframe_daily.withColumnRenamed("avg(Temperature)","Mean_Temperature")

        dataframe_daily = dataframe_daily.withColumn("Floor_Id",split(dataframe_daily["Floor_Id_Sensor_Id"]," ").getItem(0).cast("integer"))
        dataframe_daily = dataframe_daily.withColumn("Sensor_Id",split(dataframe_daily["Floor_Id_Sensor_Id"]," ").getItem(1).cast("integer"))

        dataframe_daily = dataframe_daily.orderBy("Date")

        dataframe_daily = dataframe_daily.orderBy(col("Date").asc(),col("Mean_temperature").asc())

        return dataframe_daily.select("Date","Floor_Id","Sensor_Id","Mean_Temperature")

    def print_dataframe(self):
        print (self.__dataframe.show())

    def get_dataframe(self):
        return self.__dataframe

   

# if __name__ == "__main__":
#     dataAnalysis = DataAnalysis()

#     print("### Sample Data Frame ###")
#     dataAnalysis.print_dataframe()

#     print("### Data Frame Schema ###")
#     print(dataAnalysis.get_dataframe().printSchema())

#     print("### Hourly Average Temperature on Each Floor ###")
#     data = dataAnalysis.get_hourly_avg_temperature()
#     print(data.show(data.count(),False))

#     print("### Daily Maximum Temperature on Each Floor ###")
#     data = dataAnalysis.get_daily_max_temperature()
#     print(data.show(data.count(),False))

#     print("### Daily Minimum Temperature on Each Floor ###")
#     data = dataAnalysis.get_daily_min_temperature()
#     print(data.show(data.count(),False))
    
#     print("### Daily Average Temperature Recorded by Each Temperature Sensor on Each Floor ###")
#     data = dataAnalysis.get_daily_avg_temperature_with_sensor()
#     print(data.show(data.count(),False))