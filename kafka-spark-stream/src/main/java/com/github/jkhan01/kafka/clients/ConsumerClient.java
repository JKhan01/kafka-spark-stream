package com.github.jkhan01.kafka.clients;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;


import com.github.jkhan01.kafka.constants.ApplicationConstants;
import com.github.jkhan01.kafka.constants.KafkaServerConstants;

/**
 * 
 * The Consumer Client to the Kafka Server. The Consumer will Consumer the
 * Necessary Sensor Data as JSON String.
 * 
 * @author Mohd Jamaluddin Khan
 *
 */

public class ConsumerClient {

	private KafkaConsumer<String, String> consumer;

	public ConsumerClient() {
		super();
		// TODO Auto-generated constructor stub
		Properties properties = new Properties();
		properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaServerConstants.getSERVER_URL());
		properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
				KafkaServerConstants.getKEY_DESERIALIZER_STRING());
		properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
				KafkaServerConstants.getVALUE_DESERIALIZER_STRING());
		properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, KafkaServerConstants.getGROUP_NAME());
		properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, KafkaServerConstants.getRESET_OFFSET_STATE());

		this.consumer = new KafkaConsumer<String, String>(properties);
		this.subscribeTopic();
	}

	
	public KafkaConsumer<String, String> getConsumer() {
		return consumer;
	}


	public void setConsumer(KafkaConsumer<String, String> consumer) {
		this.consumer = consumer;
	}


	public void subscribeTopic() {
		Collection<String> topics = new ArrayList<String>();
		topics.add(KafkaServerConstants.getTOPIC_NAME());
		this.consumer.subscribe(topics);
	}

	// Get Data By Polling the Broker and awaiting response for the threshold interval set in the KafkaServerConstants class
	//  Under Constants package.
	public ConsumerRecords<String, String> fetchDataFromServer() {
		ConsumerRecords<String, String> records = this.consumer
					.poll(Duration.ofMillis(ApplicationConstants.getPUBLISHING_INTERVAL_IN_MILLIS()));
		

		
		return records;
	}
	
	public void closeConsumerClient() {
		this.consumer.close();
	}

	
	
//	################### Testing The Consumer Client ###################
	public static void main(String[] args) {
		
		ConsumerClient client = new ConsumerClient();
		for (ConsumerRecord<String, String> record: client.fetchDataFromServer()) {
			System.out.println("Fetched Message: " + record.value());
		}
		
		client.closeConsumerClient();
	}

}
