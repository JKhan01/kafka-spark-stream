package com.github.jkhan01.stream.views;

import java.io.FileWriter;
import java.io.IOException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.github.jkhan01.kafka.clients.ConsumerClient;
import com.github.jkhan01.kafka.constants.ApplicationConstants;

/**
 * 
 * The Consumer View which uses the ConsumerClient to fetch the ConsumerRecords or the Data.
 * It then converts it into JSON Object and finally Exports it to the target CSV File. 
 * 
 * @author Mohd Jamaluddin Khan
 *
 */
public class ConsumerView {
	
	// The Method to convert Consumer Records to JSONObjects and append it into CSV
	public static void printToCSV(ConsumerRecord< String, String> record) throws ParseException, IOException {
		JSONParser parser = new JSONParser();  
		JSONObject json = (JSONObject) parser.parse(record.value());
		
		
		FileWriter csvWriter = new FileWriter(ApplicationConstants.getDATA_CSV_FILE_PATH(), true);
		
		csvWriter.append(json.get("timestamp").toString()+",");
		csvWriter.append(json.get("uniqueId").toString()+",");
		csvWriter.append(json.get("floorNumber")+",");
		csvWriter.append(json.get("sensorId")+",");
		csvWriter.append(json.get("temperature").toString() + "\n");
		
		csvWriter.flush();
		csvWriter.close();
		
	}
	

	// Run The Consumer Unless Terminated by the User.
	public static void main(String[] args) {
		ConsumerClient client = new ConsumerClient();
		while (true) {
			for (ConsumerRecord<String, String> record: client.fetchDataFromServer()) {
				System.out.println("Fetched Message: " + record.value());
				try {
					ConsumerView.printToCSV(record);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				}
			}	
		}
		
	}

}
