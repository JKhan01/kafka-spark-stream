package com.github.jkhan01.kafka.clients;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jkhan01.kafka.constants.ApplicationConstants;
import com.github.jkhan01.kafka.constants.KafkaServerConstants;
import com.github.jkhan01.kafka.utilities.ProducerDataGenerator;

/**
 * 
 * The Producer Client to the Kafka Server. The Producer will publish the
 * Necessary Sensor Data as JSON String
 * 
 * @author Mohd Jamaluddin Khan
 *
 */
public class ProducerClient {

	private KafkaProducer<String, String> producer;

	// Logger Object to Generate Feedback Log as Callback Method.
	private static Logger logger = LoggerFactory.getLogger(ProducerClient.class);

	public ProducerClient() {
		super();
		// TODO Auto-generated constructor stub

		Properties properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaServerConstants.getSERVER_URL());
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
				KafkaServerConstants.getKEY_SERIALIZER_STRING());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
				KafkaServerConstants.getVALUE_SERIALIZER_STRING());

		this.producer = new KafkaProducer<String, String>(properties);

	}

	// Generate and Get the Data from the DataGenerator Module of the Utilities package.
	public String getSensorData() {
		ProducerDataGenerator dataGenerator = new ProducerDataGenerator();
		return dataGenerator.generateSensorDataJSON().toString();
	}

	public void publishSensorData() {

		ProducerRecord<String, String> record = new ProducerRecord<String, String>(KafkaServerConstants.getTOPIC_NAME(),
				this.getSensorData());
		this.producer.send(record, new Callback() {

			@Override
			public void onCompletion(RecordMetadata metadata, Exception exception) {
				// TODO Auto-generated method stub
				if (exception != null) {
					// Log the Error
					logger.error("Error Occured in Publishing Data to the Kafka Cluster.\n Error Message: "
							+ exception.getMessage());
				} else {
					// Log the Basic Message Metadata
					logger.info("Sensor Data Published Successfully!" + "\n Topic: " + metadata.topic()
							+ "\n Partition Number: " + metadata.partition() + "\n Offset: " + metadata.offset());
				}
			}
		});
		// Publish the queued data onto the Broker.
		this.producer.flush();
	}
	
	// Safely Close the Producer Client
	public void closeProducerClient() {

		this.producer.close();
	}

	
//	################### Testing The Producer Client ###################
	public static void main(String[] args) {
		ProducerClient producerClient = new ProducerClient();
		TimeUnit timer = TimeUnit.MILLISECONDS;
		int i = 0;
		while (i < 10) {

			try {
				producerClient.publishSensorData();
				i++;
				timer.sleep(ApplicationConstants.getPUBLISHING_INTERVAL_IN_MILLIS());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());

			}
		}

		logger.info("Producer Client Shutting Down!");
		producerClient.closeProducerClient();
	}

}
