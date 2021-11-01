package com.github.jkhan01.kafka.clients;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.github.jkhan01.kafka.constants.KafkaServerConstants;
import com.github.jkhan01.kafka.utilities.ProducerDataGenerator;

public class ProducerClient {
	
	private KafkaProducer<String, String> producer;
	

	public ProducerClient() {
		super();
		// TODO Auto-generated constructor stub
		
		Properties properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaServerConstants.getSERVER_URL());
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, KafkaServerConstants.getKEY_SERIALIZER_STRING());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaServerConstants.getVALUE_SERIALIZER_STRING());
		
		this.producer = new KafkaProducer<String, String>(properties);
	}
	
	private String getSensorData() {
		ProducerDataGenerator dataGenerator = new ProducerDataGenerator();
		return dataGenerator.generateSensorDataJSON().toString();
	}
	
	private void publishSensorData() {
		ProducerRecord<String, String> record = new ProducerRecord<String, String>(KafkaServerConstants.getTOPIC_NAME(), this.getSensorData());
		this.producer.send(record);
		this.producer.flush();
	}
	
	private void closeProducerClient() {
		this.producer.close();
	}
	
	public static void main(String[] args) {
		ProducerClient producerClient = new ProducerClient();
		TimeUnit timer = TimeUnit.MILLISECONDS;
		int i = 0;
		while (i<20) {
			
			try {
				producerClient.publishSensorData();
				i++;
				timer.sleep(500);
				System.out.println("Message Published Successfully!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
				
			}
		}
		
		producerClient.closeProducerClient();
	}
	

}
