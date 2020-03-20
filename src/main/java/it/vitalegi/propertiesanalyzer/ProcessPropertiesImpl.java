package it.vitalegi.propertiesanalyzer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.vitalegi.propertiesanalyzer.matcher.Matcher;
import it.vitalegi.propertiesanalyzer.writer.DocumentWriter;

public class ProcessPropertiesImpl {

	Logger log = LoggerFactory.getLogger(ProcessPropertiesImpl.class);

	DocumentWriter writer;
	List<PropertiesAlias> properties;
	List<Matcher> matchers;

	public ProcessPropertiesImpl() {
		super();
	}

	public ProcessPropertiesImpl(List<PropertiesAlias> properties, List<Matcher> matchers, DocumentWriter writer) {
		super();
		this.properties = properties;
		this.matchers = matchers;
		this.writer = writer;
	}

	public void process() {

		List<String> keys = getKeys();

		printHeader();
		for (String key : keys) {
			processKey(key);
		}
	}

	protected void printHeader() {
		writer.h1("Diff");
		writer.h2("Matchers used");
		matchers.forEach(matcher -> writer.list(matcher.name() + ": " + matcher.description()));
	}

	protected List<String> getKeys() {
		List<String> keys = getKeys(properties);
		keys.sort(Comparator.comparing(String::toString));
		return keys;
	}

	protected void processKey(String key) {
		writer.h2(key);
		if (hasToBePrinted(key)) {
			List<String> values = getValues(key);
			for (int i = 0; i < properties.size(); i++) {
				processValue(key, properties.get(i).getAlias(), values.get(i));
			}
		}
	}

	protected boolean hasToBePrinted(String key) {
		List<String> values = getValues(key);
		return hasToBePrinted(values);
	}

	protected boolean hasToBePrinted(List<String> values) {
		for (Matcher matcher : matchers) {
			if (matcher.anyMatches(values) && matcher.anyNotMatches(values)) {
				return true;
			}
		}
		return false;
	}

	protected void processValue(String key, String alias, String value) {
		writer.h3("Value: `" + value + "` - " + alias);

		for (Matcher matcher : matchers) {
			if (isInteresting(matcher, key, value)) {
				writer.list(matcher.name());
			}
		}
		writer.newLine();
	}

	protected boolean isInteresting(Matcher matcher, String key, String value) {
		List<String> values = getValues(key);
		return isInteresting(matcher, values, value);
	}

	protected boolean isInteresting(Matcher matcher, List<String> values, String value) {
		if (!matcher.anyMatches(values)) {
			return false;
		}
		boolean match = matcher.matches(value);
		if (match) {
			return false;
		}
		return true;
	}

	protected List<String> getValues(String key) {
		return getValues(properties, key);
	}

	protected List<String> getValues(List<PropertiesAlias> properties, String key) {
		List<String> values = new ArrayList<>();
		for (PropertiesAlias prop : properties) {
			values.add(prop.getProperties().getProperty(key));
		}
		return values;
	}

	protected List<String> getKeys(List<PropertiesAlias> properties) {

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