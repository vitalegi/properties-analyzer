package it.vitalegi.propertiesanalyzer;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GetPropertiesServiceImpl {

	Logger log = LoggerFactory.getLogger(GetPropertiesServiceImpl.class);

	public List<String> getValues(List<PropertiesAlias> properties, String key) {
		List<String> values = new ArrayList<>();
		for (PropertiesAlias prop : properties) {
			values.add(prop.getProperties().getProperty(key));
		}
		return values;
	}

	public List<String> getKeys(List<PropertiesAlias> properties) {

		List<String> keys = new ArrayList<>();

		for (PropertiesAlias prop : properties) {
			Enumeration<?> names = prop.getProperties().propertyNames();
			while (names.hasMoreElements()) {
				String next = names.nextElement().toString();
				if (!keys.contains(next)) {
					keys.add(next);
				}
			}
		}
		return keys;
	}
}