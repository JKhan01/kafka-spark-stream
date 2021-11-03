

from AnalyserModules.DataAnalysis import DataAnalysis
from AnalyserModules.JSONGenerator import JSONGenerator



def user_interface():
    print ("##############  Welcome  ################")
    print ("""Select The Option From Below:\n 
    1. Export Hourly Average Temperature on Each Floor
    2. Export Daily Minimum - Maximum Temperature on Each Floor
    3. Export Daily Average Temperature Reading by Sensors.
    4. Print Data Sample
    5. Exit
    """)

def main():
    analyser = DataAnalysis()

    json_generator = JSONGenerator(analyser=analyser)

    user_interface()
    option = int(input("\nEnter the Option Number: "))
    while (option!=5):
        if (option == 1):
            json_generator.store_hourly_avg_temperature()
            
        elif (option == 2):
            json_generator.store_daily_min_max_temperature()

        elif (option == 3):
            json_generator.store_daily_avg_temperature_by_sensor()

        elif (option == 4):
            print (analyser.get_dataframe().show())

        else:
            print ("Invalid Input")

        user_interface()
        option = int(input("\nEnter the Option Number: "))
    



if (__name__ == "__main__"):
    main()
