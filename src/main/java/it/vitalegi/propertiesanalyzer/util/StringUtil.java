package it.vitalegi.propertiesanalyzer.util;

public class StringUtil {

	public static boolean isNotNull(String value) {
		return !isNullOrEmpty(value);
	}

	public static boolean isNullOrEmpty(String value) {
		return value == null || value.equals("");
	}

}
