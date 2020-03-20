package it.vitalegi.propertiesanalyzer;

import java.util.Comparator;
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

	PropertiesUtilServiceImpl propertiesUtilService;

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
		List<String> keys = propertiesUtilService.getKeys(properties);
		keys.sort(Comparator.comparing(String::toString));
		return keys;
	}

	protected void processKey(String key) {
		writer.h2(key);
		List<String> values = getValues(key);
		if (propertiesUtilService.hasMismatch(matchers, values)) {
			for (int i = 0; i < properties.size(); i++) {
				processValue(key, properties.get(i).getAlias(), values.get(i));
			}
		}
	}

	protected void processValue(String key, String alias, String value) {
		writer.h3("Value: `" + value + "` - " + alias);

		for (Matcher matcher : matchers) {
			List<String> values = getValues(key);
			if (propertiesUtilService.isInteresting(matcher, values, value)) {
				writer.list(matcher.name());
			}
		}
		writer.newLine();
	}

	protected List<String> getValues(String key) {
		return propertiesUtilService.getValues(properties, key);
	}

}