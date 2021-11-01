package com.github.jkhan01.kafka.constants;

import org.apache.kafka.common.serialization.StringSerializer;

public class KafkaServerConstants {

	private static String SERVER_URL = "127.0.0.1:9092";
	private static String KEY_SERIALIZER_STRING = StringSerializer.class.getName();
	private static String VALUE_SERIALIZER_STRING = StringSerializer.class.getName();

	public static String getSERVER_URL() {
		return SERVER_URL;
	}

	public static String getKEY_SERIALIZER_STRING() {
		return KEY_SERIALIZER_STRING;
	}

	public static String getVALUE_SERIALIZER_STRING() {
		return VALUE_SERIALIZER_STRING;
	}

}
