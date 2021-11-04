

from AnalyserModules.DataAnalysis import DataAnalysis
from AnalyserModules.JSONGenerator import JSONGenerator



def user_interface():
    '''
        The User Interface Providing the Use cases as option to the user.
    '''
    
    print ("##############  Welcome  ################")
    print ("""Select The Option From Below:\n 
    1. Export Hourly Average Temperature on Each Floor
    2. Export Daily Minimum - Maximum Temperature on Each Floor
    3. Export Daily Average Temperature Reading by Sensors.
    4. Print Data Sample
    5. Print Analysis Data
    6. Exit
    """)


def main():
    '''
        The Main Function to execute and meet the analysis use cases. It calls the JSONGenerator with the 
        Data Analysis Module's object as parameter.
   '''

    analyser = DataAnalysis()

    json_generator = JSONGenerator(analyser=analyser)

    user_interface()
    option = int(input("\nEnter the Option Number: "))
    while (option!=6):
        if (option == 1):
            json_generator.store_hourly_avg_temperature()
            
        elif (option == 2):
            json_generator.store_daily_min_max_temperature()

        elif (option == 3):
            json_generator.store_daily_avg_temperature_by_sensor()

        elif (option == 4):
            print (analyser.get_dataframe().show())

        elif (option == 5):
            dataAnalysis = DataAnalysis()

            print("### Sample Data Frame ###")
            dataAnalysis.print_dataframe()

            print("### Data Frame Schema ###")
            print(dataAnalysis.get_dataframe().printSchema())

            print("### Hourly Average Temperature on Each Floor ###")
            data = dataAnalysis.get_hourly_avg_temperature()
            print(data.show(data.count(),False))

            print("### Daily Maximum Temperature on Each Floor ###")
            data = dataAnalysis.get_daily_max_temperature()
            print(data.show(data.count(),False))

            print("### Daily Minimum Temperature on Each Floor ###")
            data = dataAnalysis.get_daily_min_temperature()
            print(data.show(data.count(),False))
            
            print("### Daily Average Temperature Recorded by Each Temperature Sensor on Each Floor ###")
            data = dataAnalysis.get_daily_avg_temperature_with_sensor()
            print(data.show(data.count(),False))
        else:
            print ("Invalid Input")

        user_interface()
        option = int(input("\nEnter the Option Number: "))
    



if (__name__ == "__main__"):
    main()
