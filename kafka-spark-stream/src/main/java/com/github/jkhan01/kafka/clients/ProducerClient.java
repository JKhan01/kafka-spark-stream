package com.github.jkhan01.kafka.clients;

import com.github.jkhan01.kafka.utilities.ProducerDataGenerator;

public class ProducerClient {
	
	public static void main(String[] args) {
		ProducerDataGenerator data = new ProducerDataGenerator();
		System.out.println(data.generateSensorDataJSON());
	}

}
