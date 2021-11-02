package com.github.jkhan01.stream.views;


import java.util.concurrent.TimeUnit;



import com.github.jkhan01.kafka.clients.ProducerClient;
import com.github.jkhan01.kafka.constants.ApplicationConstants;

public class ProducerView {
	
	public static void main(String[] args) {
		ProducerClient client = new ProducerClient();

		TimeUnit timer = TimeUnit.MILLISECONDS;

		
		while(true) {
			try {
				client.publishSensorData();

				timer.sleep(ApplicationConstants.getPUBLISHING_INTERVAL_IN_MILLIS());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());

			}
		}
		
		
		

		
		
	}

}
