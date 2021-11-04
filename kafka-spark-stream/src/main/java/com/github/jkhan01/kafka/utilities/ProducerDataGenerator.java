package com.github.jkhan01.kafka.utilities;

import java.sql.Timestamp;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.json.simple.JSONObject;

import com.github.jkhan01.kafka.constants.ApplicationConstants;

/**
 * 
 * Generate the Random Sensor data for the producer to serve the kafka cluster
 * @author Mohd Jamaluddin Khan
 *
 */

public class ProducerDataGenerator {

	private Random random = new Random();

	// The Method to Generate The Realtime Temperature Sensor Data in the Form of JSONObject.
	public JSONObject generateSensorDataJSON() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("sensorId", this.generateSensorId());
		jsonObject.put("floorNumber", this.generateFloorNumber());
		jsonObject.put("uniqueId", this.generateUniqueId());
		jsonObject.put("timestamp", this.generateTimestamp());
		jsonObject.put("temperature", this.generateTemperature());

		return jsonObject;

	}

	// Generating the Sensor ID [0-5) randomly.
	private int generateSensorId() {
		int numberOfSensorsPerFloor = ApplicationConstants.getNUMBER_OF_SENSORS_PER_FLOOR();

		return random.nextInt(numberOfSensorsPerFloor);
	}

	// Generating the Floor Number [0-9) randomly.
	private int generateFloorNumber() {
		int numberOfFloors = ApplicationConstants.getNUMBER_OF_FLOORS();

		return random.nextInt(numberOfFloors);
	}

	// Generate The Double Type Temperature Data.
	// Considering the ideal, real-world average temperatutre to be between 20'C and 40'C
	private double generateTemperature() {
		double rangeDifference = ApplicationConstants.getMAXIMUM_FLOOR_TEMPERATURE()
				- ApplicationConstants.getMINIMUM_FLOOR_TEMPERATURE();
		double minimumTemperature = ApplicationConstants.getMINIMUM_FLOOR_TEMPERATURE();

		return (random.nextInt((int) rangeDifference) + minimumTemperature + random.nextDouble());
	}

	// Generating the Current Timestamp for the sensor data
	private String generateTimestamp() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		return timestamp.toString();
	}

	// Generate the Unique ID for each JSON Object / CSV Row using the UUID Library
	private String generateUniqueId() {

		return UUID.randomUUID().toString();
	}
	
	
	// Testing the successful Generation of the JSON .
	public static void main(String[] args) {
		ProducerDataGenerator data = new ProducerDataGenerator();
		System.out.println(data.generateSensorDataJSON());
	}

}
