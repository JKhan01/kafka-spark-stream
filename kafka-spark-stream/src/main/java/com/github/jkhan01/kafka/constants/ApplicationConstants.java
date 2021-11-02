package com.github.jkhan01.kafka.constants;

public class ApplicationConstants {

	private static int NUMBER_OF_SENSORS_PER_FLOOR = 5;
	private static int NUMBER_OF_FLOORS = 10;

	private static double MINIMUM_FLOOR_TEMPERATURE = 20;
	private static double MAXIMUM_FLOOR_TEMPERATURE = 40;
	
	private static int PUBLISHING_INTERVAL_IN_MILLIS = 1000;

	
	public static int getPUBLISHING_INTERVAL_IN_MILLIS() {
		return PUBLISHING_INTERVAL_IN_MILLIS;
	}

	public static int getNUMBER_OF_SENSORS_PER_FLOOR() {
		return NUMBER_OF_SENSORS_PER_FLOOR;
	}

	public static int getNUMBER_OF_FLOORS() {
		return NUMBER_OF_FLOORS;
	}

	public static double getMINIMUM_FLOOR_TEMPERATURE() {
		return MINIMUM_FLOOR_TEMPERATURE;
	}

	public static double getMAXIMUM_FLOOR_TEMPERATURE() {
		return MAXIMUM_FLOOR_TEMPERATURE;
	}

}
