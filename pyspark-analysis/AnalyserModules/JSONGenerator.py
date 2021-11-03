
from .DataAnalysis import DataAnalysis
import json
from .ParameterConstants import hourly_avg_temp_file_path,daily_min_max_temp_file_path, daily_avg_temp_by_sensor_file_path

class JSONGenerator:

    def __init__(self,analyser:DataAnalysis) -> None:
        self.analyser = analyser

    def store_hourly_avg_temperature(self):
        data = self.analyser.get_hourly_avg_temperature()
        
        data = data.withColumn("Timestamp",data["Timestamp"].cast("string"))
        target_data = data.collect()
        

        json_data = []
        for row in target_data:
            json_data.append({"timestamp":row[0],"floor_number":row[1],"mean_temperature":row[2]})
        
        with open(hourly_avg_temp_file_path,"w") as jsonFile:
            json.dump(json_data,jsonFile,indent=4)

    
    def store_daily_min_max_temperature(self):
        data_max = self.analyser.get_daily_max_temperature()
        data_max = data_max.withColumn("Date",data_max["Date"].cast("string")).collect()

        data_min = self.analyser.get_daily_min_temperature()
        data_min = data_min.withColumn("Date",data_min["Date"].cast("string")).collect()

        json_data = []

        for row in data_max:
            json_data.append({"date":row[0],"floor_number":row[1],"temperature":row[2],"isMax":True})

        for row in data_min:
            json_data.append({"date":row[0],"floor_number":row[1],"temperature":row[2],"isMax":False})

        with open(daily_min_max_temp_file_path,"w") as jsonFile:
            json.dump(json_data,jsonFile,indent=4)


    def store_daily_avg_temperature_by_sensor(self):

        data = self.analyser.get_daily_avg_temperature_with_sensor()
        data = data.withColumn("Date",data["Date"].cast("string"))

        target_data = data.collect()

        json_data = []

        for row in target_data:
            json_data.append({"date":row[0],"floor_number":row[1],"sensor_id":row[2],"mean_temperature":row[3]})
        
        with open(daily_avg_temp_by_sensor_file_path,"w") as jsonFile:
            json.dump(json_data,jsonFile,indent=4)