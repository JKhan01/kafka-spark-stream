package com.github.jkhan01.kafka.constants;

import org.apache.kafka.common.serialization.StringSerializer;

public class KafkaServerConstants {

	private static String SERVER_URL = "127.0.0.1:9092";
	private static String KEY_SERIALIZER_STRING = StringSerializer.class.getName();
	private static String VALUE_SERIALIZER_STRING = StringSerializer.class.getName();
	
	private static String TOPIC_NAME = "java-topic";
	
	private static String GROUP_NAME = "java-workaround-group";

	public static String getSERVER_URL() {
		return SERVER_URL;
	}

	public static String getKEY_SERIALIZER_STRING() {
		return KEY_SERIALIZER_STRING;
	}

	public static String getVALUE_SERIALIZER_STRING() {
		return VALUE_SERIALIZER_STRING;
	}

	public static String getTOPIC_NAME() {
		return TOPIC_NAME;
	}

	public static String getGROUP_NAME() {
		return GROUP_NAME;
	}
	
	

}
