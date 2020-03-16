package it.vitalegi.propertiesanalyzer.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import it.vitalegi.propertiesanalyzer.PropertiesAlias;

public class PropertiesUtil {

	public static Properties readFile(String filepath) {
		try (InputStream input = new FileInputStream(filepath)) {

			Properties prop = new Properties();

			prop.load(input);

			return prop;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static PropertiesAlias createProperties(String alias, String... entries) {

		Properties p = new Properties();

		for (int i = 0; i < entries.length; i += 2) {
			p.setProperty(entries[i], entries[i + 1]);
		}
		PropertiesAlias pa = new PropertiesAlias();
		pa.setAlias(alias);
		pa.setProperties(p);
		return pa;
	}
}
