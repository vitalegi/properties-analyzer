package it.vitalegi.propertiesanalyzer;

import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.vitalegi.propertiesanalyzer.matcher.SingleMatcher;
import it.vitalegi.propertiesanalyzer.writer.DocumentWriter;

public class ProcessPropertiesImpl {

	Logger log = LoggerFactory.getLogger(GetPropertiesServiceImpl.class);

	GetPropertiesServiceImpl getPropertiesService;
	DocumentWriter writer;
	List<PropertiesAlias> properties;
	List<SingleMatcher> matchers;

	public ProcessPropertiesImpl(GetPropertiesServiceImpl getPropertiesService, List<PropertiesAlias> properties,
			List<SingleMatcher> matchers, DocumentWriter writer) {
		super();
		this.getPropertiesService = getPropertiesService;
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
		matchers.forEach(matcher -> writer.list(getMatcherName(matcher)));
	}

	protected List<String> getKeys() {
		List<String> keys = getPropertiesService.getKeys(properties);
		keys.sort(Comparator.comparing(String::toString));
		return keys;
	}

	protected void processKey(String key) {
		writer.h2(key);
		if (anyMatcherWithAnyMatch(key)) {
			List<String> values = getValues(key);
			for (int i = 0; i < properties.size(); i++) {
				processValue(key, properties.get(i).getAlias(), values.get(i));
			}
		}
	}

	protected boolean anyMatcherWithAnyMatch(String key) {
		List<String> values = getValues(key);
		for (SingleMatcher matcher : matchers) {
			if (matcher.anyMatches(values)) {
				return true;
			}
		}
		return false;
	}

	protected void processValue(String key, String alias, String value) {
		writer.h3("Value: `" + value + "` - " + alias);

		for (SingleMatcher matcher : matchers) {
			processValueOnSingleMatcher(matcher, key, value);
		}
		writer.newLine();
	}

	protected void processValueOnSingleMatcher(SingleMatcher matcher, String key, String value) {
		List<String> values = getValues(key);

		if (!matcher.anyMatches(values)) {
			return;
		}
		boolean match = matcher.matches(value);
		if (match) {
			return;
		}
		writer.list(getMatcherName(matcher) + ": KO");
	}

	protected List<String> getValues(String key) {
		return getPropertiesService.getValues(properties, key);
	}

	protected String getMatcherName(SingleMatcher matcher) {
		return matcher.getClass().getSimpleName();
	}
}