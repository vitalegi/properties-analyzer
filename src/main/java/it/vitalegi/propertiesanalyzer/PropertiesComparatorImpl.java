package it.vitalegi.propertiesanalyzer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import it.vitalegi.propertiesanalyzer.matcher.Matcher;
import it.vitalegi.propertiesanalyzer.writer.DocumentWriter;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PropertiesComparatorImpl implements PropertiesProcessor {
	Logger log = LoggerFactory.getLogger(PropertiesComparatorImpl.class);

	public static final String MODE = "tabular";

	DocumentWriter writer;
	List<PropertiesAlias> properties;
	List<Matcher> matchers;

	@Autowired
	PropertiesUtilServiceImpl propertiesUtilService;

	public PropertiesComparatorImpl() {
		super();
	}

	public void setWriter(DocumentWriter writer) {
		this.writer = writer;
	}

	public void setProperties(List<PropertiesAlias> properties) {
		this.properties = properties;
	}

	public void setMatchers(List<Matcher> matchers) {
		this.matchers = matchers;
	}

	@Override
	public void process() {
		List<String> keys = getKeys();

		writer.h1("Compare");
		List<String> headers = new ArrayList<>();
		headers.add("Key");
		properties.stream().map(PropertiesAlias::getAlias).forEach(headers::add);
		headers.add("Interesting Matchers");

		writer.tableHeaders(headers);

		for (String key : keys) {
			processKey(key);
		}
	}

	protected List<String> getKeys() {
		List<String> keys = propertiesUtilService.getKeys(properties);
		keys.sort(Comparator.comparing(String::toString));
		return keys;
	}

	protected void processKey(String key) {
		List<String> values = getValues(key);

		List<String> row = new ArrayList<>();

		row.add(key);
		values.stream().map(s -> "`" + s + "`").forEach(row::add);

		row.add(propertiesUtilService.getMismatchMatchers(matchers, values)//
				.stream().map(Matcher::name).collect(Collectors.joining(", ")));

		writer.tableRow(row);

	}

	protected List<String> getValues(String key) {
		return propertiesUtilService.getValues(properties, key);
	}
}
