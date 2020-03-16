package it.vitalegi.propertiesanalyzer;

import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.vitalegi.propertiesanalyzer.matcher.Matcher;
import it.vitalegi.propertiesanalyzer.matcher.SingleMatcher;

public class AnalyzeToMarkdownImpl {

	Logger log = LoggerFactory.getLogger(AnalyzePropertiesServiceImpl.class);

	AnalyzePropertiesServiceImpl analyzePropertiesService;
	PrintWriter writer;
	List<PropertiesAlias> properties;
	Matcher filterBy;
	List<SingleMatcher> showMatchers;

	public AnalyzeToMarkdownImpl(AnalyzePropertiesServiceImpl analyzePropertiesService,
			List<PropertiesAlias> properties, Matcher filterBy, List<SingleMatcher> showMatchers, PrintWriter writer) {
		super();
		this.analyzePropertiesService = analyzePropertiesService;
		this.properties = properties;
		this.filterBy = filterBy;
		this.showMatchers = showMatchers;
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
		writer.append("# Diff\n\n");
		writer.append("## Matchers used\n\n");

		showMatchers.forEach(matcher -> writer.append("- " + matcher.getClass().getName() + "\n"));
	}

	protected List<String> getKeys() {
		List<String> keys = analyzePropertiesService.getKeys(properties);
		keys.sort(Comparator.comparing(String::toString));
		return keys;
	}

	protected void processKey(String key) {
		List<String> values = getValues(key);
		if (matchesFilterBy(values)) {
			processKeyWithMatch(key);
		}
	}

	protected List<String> getValues(String key) {
		return analyzePropertiesService.getValues(properties, key);
	}

	protected boolean matchesFilterBy(List<String> values) {
		return filterBy.matches(values);
	}

	protected void processKeyWithMatch(String key) {
		printKeyHeader(key);

		List<String> values = getValues(key);
		for (int i = 0; i < properties.size(); i++) {
			processValueWithMatch(key, properties.get(i).getAlias(), values.get(i));
		}
	}

	protected void printKeyHeader(String key) {
		writer.append("## Key: " + key + "\n");
		writer.append("\n");
	}

	protected void processValueWithMatch(String key, String alias, String value) {
		writer.append("### Value: `" + value + "` - (" + alias + ")\n");
		writer.append("\n");

		for (SingleMatcher showMatcher : showMatchers) {
			processValueOnSingleMatcher(showMatcher, key, value);
		}
		writer.append("\n");
	}

	protected void processValueOnSingleMatcher(SingleMatcher showMatcher, String key, String value) {
		boolean warning = warning(showMatcher, key, value);
		boolean match = match(showMatcher, value);

		if (match) {
			if (warning) {
				writer.append(
						"- " + showMatcher.getClass().getName() + ": " + showMatcher.matches(value) + " WARNING!\n");
			} else {
				writer.append("- " + showMatcher.getClass().getName() + ": " + showMatcher.matches(value) + "\n");
			}
		}
	}

	protected boolean match(SingleMatcher matcher, String value) {
		return matcher.matches(value);
	}

	protected boolean warning(SingleMatcher matcher, String key, String value) {
		List<String> values = getValues(key);

		if (matcher.matches(value)) {
			if (!matcher.matches(values)) {
				return true;
			}
		}
		return false;
	}
}