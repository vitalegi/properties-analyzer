package it.vitalegi.propertiesanalyzer;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import it.vitalegi.propertiesanalyzer.matcher.Matcher;

@Service
public class PropertiesUtilServiceImpl {

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

	public boolean isInteresting(Matcher matcher, List<String> values, String value) {
		if (!hasMismatch(matcher, values)) {
			return false;
		}

		boolean match = matcher.matches(value);

		if (match) {
			return false;
		}
		return true;
	}

	public boolean hasMismatch(List<Matcher> matchers, List<String> values) {
		return !getMismatchMatchers(matchers, values).isEmpty();
	}

	public List<Matcher> getMismatchMatchers(List<Matcher> matchers, List<String> values) {
		return matchers.stream()//
				.filter(matcher -> hasMismatch(matcher, values))//
				.collect(Collectors.toList());
	}

	public boolean hasMismatch(Matcher matcher, List<String> values) {
		return matcher.anyMatches(values) && matcher.anyNotMatches(values);
	}
}
