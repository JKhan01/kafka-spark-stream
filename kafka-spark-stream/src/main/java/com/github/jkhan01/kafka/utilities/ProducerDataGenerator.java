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

	public JSONObject generateSensorDataJSON() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("sensorId", this.generateSensorId());
		jsonObject.put("floorNumber", this.generateFloorNumber());
		jsonObject.put("uniqueId", this.generateUniqueId());
		jsonObject.put("timestamp", this.generateTimestamp());
		jsonObject.put("temperature", this.generateTemperature());

		return jsonObject;

	}

	private int generateSensorId() {
		int numberOfSensorsPerFloor = ApplicationConstants.getNUMBER_OF_SENSORS_PER_FLOOR();

		return random.nextInt(numberOfSensorsPerFloor);
	}

	private int generateFloorNumber() {
		int numberOfFloors = ApplicationConstants.getNUMBER_OF_FLOORS();

		return random.nextInt(numberOfFloors);
	}

	private double generateTemperature() {
		double rangeDifference = ApplicationConstants.getMAXIMUM_FLOOR_TEMPERATURE()
				- ApplicationConstants.getMINIMUM_FLOOR_TEMPERATURE();
		double minimumTemperature = ApplicationConstants.getMINIMUM_FLOOR_TEMPERATURE();

		return (random.nextInt((int) rangeDifference) + minimumTemperature + random.nextDouble());
	}

	private String generateTimestamp() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		return timestamp.toString();
	}

	private String generateUniqueId() {

		return UUID.randomUUID().toString();
	}
	
	
	
	public static void main(String[] args) {
		ProducerDataGenerator data = new ProducerDataGenerator();
		System.out.println(data.generateSensorDataJSON());
	}

}
