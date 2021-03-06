package com.github.jkhan01.stream.views;


import java.util.concurrent.TimeUnit;



import com.github.jkhan01.kafka.clients.ProducerClient;
import com.github.jkhan01.kafka.constants.ApplicationConstants;

/**
 * 
 * The Producer View which uses the ProducerClient to generate the ProducerRecord or Data repeatedly.
 * Each Producer Record is generated by converting the JSONOBject to JSON String. 
 * 
 * @author Mohd Jamaluddin Khan
 *
 */

public class ProducerView {
	
	// Run The Producer Unless Terminated by the User.
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
