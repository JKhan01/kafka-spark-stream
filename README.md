# kafka-spark-stream

The Project and workaround repository to generate a producer stream to kafka cluster, consume and then process it.

## Guide / Walkthrough of the Project

### Software Requirements:

1. JAVA 1.8 only
2. Apache Kafka (> 2.0.x & < 3.0). Specifically For the Project Apache Kafka 2.8.1 was used.
3. Python Virtual Environment
4. Python >=3.6.x
5. Apache Spark Standalone. (Not Necessary since pyspark auto bridges with JAVA HOME and sets up a standalone Spark Server.)

### Directory Structure

```text
|--- data-csv/
        |
        |--- daily-avg-temperature-by-sensor.json
        |--- daily-min-max-temperature.json
        |--- hourly-avg-temperature.json
        |--- sensor-data.csv
        |
|--- kafka-spark-stream/
|--- pyspark-analysis/
        |
        |--- AnalyserModules/
                |
                |--- \__init__.py
                |--- DataAnalysis.py
                |--- JSONGenerator.py
                |--- ParameterConstants.py
        |--- main.py
        |--- requirements.txt
```

### IMPORTANT NOTE:

**Please Update the File Paths in the Following Two Files:**
1. [pyspark-analysis/AnalyserModules/DataConstants.py](https://github.com/JKhan01/kafka-spark-stream/blob/main/pyspark-analysis/AnalyserModules/ParameterConstants.py)

2. [kafka-spark-stream/src/main/java/com/github/jkhan01/kafka/constants/ApplicationConstants.java](https://github.com/JKhan01/kafka-spark-stream/blob/main/kafka-spark-stream/src/main/java/com/github/jkhan01/kafka/constants/ApplicationConstants.java) 

### The Project Comprises of Two Parts or sub-projects / modules

> These parts have been developed using JAVA(1.8) and Python3(3.8.x) Programming Language.

> 1. [Kafka-Spark-Stream Directory](https://github.com/JKhan01/kafka-spark-stream/tree/main/kafka-spark-stream):

>> - Developed Using **JAVA** as **Maven** Project. This sub-module Runs the Kafka Producer Consumer Clients.

>> - The Producer Generates realtime temperature sensor data using JAVA Utilities' Random Library.

>> - The Producer Publishes this data onto the Kafka Broker in the form of JSON String given below.:

```json

        {
            "sensorId":2,
            "floorNumber":5,
            "uniqueId":"fda28ff8-19e3-4432-af39-e1e159c44026",
            "timestamp":2021-11-02 21:52:19.419,
            "temperature":33.69926274
        }

```

>> - The Consumer then subscribes to the topic and saves the data by exporting it to the CSV File [sensor-data.csv](https://github.com/JKhan01/kafka-spark-stream/blob/main/data-csv/sensor-data.csv)

>> #### Setup:

>>> 1. Open the sub-directory in Eclipse or IntelliJ IDE.
>>> 2. Configure the project as Maven Project. **Make Sure to set the compiler and Maven Project version to JAVA 1.8**.
>>> 3. Perform Maven Install to download the dependancies specified in the [pom.xml](https://github.com/JKhan01/kafka-spark-stream/blob/main/kafka-spark-stream/pom.xml).
>>> 4. To Continuously publish the data to the kafka broker, start the kafka broker and run the **[ProducerView.java](https://github.com/JKhan01/kafka-spark-stream/blob/main/kafka-spark-stream/src/main/java/com/github/jkhan01/stream/views/ProducerView.java) in the views package.**
>>> 5. Simultaneously or after some time, you can export the data from the broker the CSV file by running the **[ConsumerView.java](https://github.com/JKhan01/kafka-spark-stream/blob/main/kafka-spark-stream/src/main/java/com/github/jkhan01/stream/views/ConsumerView.java) in the views package.**

> 2. [Pyspark-Analysis Directory](https://github.com/JKhan01/kafka-spark-stream/tree/main/pyspark-analysis):

>> - Once The Sensor data keeps getting updated in the CSV File mentioned above, we use **Apache Spark with python (Using Pyspark Package)** to perform the analysis.

>> - #### Setup:

>>> 1. Navigate to the sub-directory after creating a new virtual environment.

>>> 2. Install the dependencies by running the following commmand in the terminal / anaconda prompt.

``` text
    pip install -r requirements.txt
```

>>> 3. To Export the Use Case Required data to respective json files. Run the [main.py](https://github.com/JKhan01/kafka-spark-stream/blob/main/pyspark-analysis/main.py) file.

```text
    python main.py
```

### The Data CSV and Output JSON Directory - *data-csv/*:

1. [sensor-data.csv](https://github.com/JKhan01/kafka-spark-stream/blob/main/data-csv/sensor-data.csv)

> The Comma Delimited File to which the kafka consumer clients export temperature sensor-data published by the producer.

2. [daily-avg-temperature-by-sensor.json](https://github.com/JKhan01/kafka-spark-stream/blob/main/data-csv/daily-avg-temperature-by-sensor.json)

> The JSON File which receives the Output analysis of Daily Average temperature sorted in increasing order of mean temperature by each sensor for that day.

```json
    {
        "date": "2021-11-02",
        "floor_number": 6,
        "sensor_id": 1,
        "mean_temperature": 27.815895999530447
    }
```

3. [daily-min-max-temperature.json](https://github.com/JKhan01/kafka-spark-stream/blob/main/data-csv/daily-min-max-temperature.json)

> The JSON File which receives the output analysis of Daily Minimum and Maximum temperature recored on each floor.

```json
    [
        {
        "date": "2021-11-02",
        "floor_number": 8,
        "temperature": 39.915860514161736,
        "isMax": true
        },
        {
        "date": "2021-11-02",
        "floor_number": 6,
        "temperature": 20.011260566867954,
        "isMax": false
        }
    ]
```

4. [hourly-avg-temperature.json](https://github.com/JKhan01/kafka-spark-stream/blob/main/data-csv/hourly-avg-temperature.json)

> The JSON File which receives the output analysis of Hourly Average temperature recorded on Each Floor.

```json
    {
        "timestamp": "2021-11-02 21:00:00",
        "floor_number": 1,
        "mean_temperature": 31.546877235066603
    }
```
