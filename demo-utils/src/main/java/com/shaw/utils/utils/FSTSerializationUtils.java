package com.shaw.utils.utils;

import org.nustaq.serialization.FSTConfiguration;

/**
 * @author shaw
 * @date 2018年12月14日
 */
public class FSTSerializationUtils {

	private final static FSTConfiguration CONFIGURATION = FSTConfiguration.createDefaultConfiguration();

	public static <T> byte[] serialize(T obj) {
		return CONFIGURATION.asByteArray(obj);
	}

	@SuppressWarnings("unchecked")
	public static <T> T deserialize(byte[] objectData) {
		return (T) CONFIGURATION.asObject(objectData);
	}

	public static <T> T clone(T obj) {
		return deserialize(serialize(obj));
	}

}
