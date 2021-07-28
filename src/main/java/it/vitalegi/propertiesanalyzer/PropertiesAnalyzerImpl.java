package it.vitalegi.propertiesanalyzer;

import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import it.vitalegi.propertiesanalyzer.matcher.Matcher;
import it.vitalegi.propertiesanalyzer.util.StringUtil;
import it.vitalegi.propertiesanalyzer.writer.DocumentWriter;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PropertiesAnalyzerImpl {

	Logger log = LoggerFactory.getLogger(PropertiesAnalyzerImpl.class);

	DocumentWriter writer;
	List<PropertiesAlias> properties;
	List<Matcher> matchers;

	@Autowired
	PropertiesUtilServiceImpl propertiesUtilService;

	public PropertiesAnalyzerImpl() {
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
		List<String> values = getValues(key);
		if (propertiesUtilService.trigger(matchers, values)) {
			writer.h2(key);
			for (Matcher matcher : matchers) {
				if (matcher.trigger(values)) {
					writer.list(matcher.name() + ": " + matcher.description());
				}
			}
			writer.newLine();
			for (int i = 0; i < properties.size(); i++) {
				processValue(key, properties.get(i).getAlias(), values.get(i));
			}
		}
	}

	protected void processValue(String key, String alias, String value) {
		if (StringUtil.isNullOrEmpty(value)) {
			writer.h3("Value: `null` - " + alias);
		} else {
			writer.h3("Value: `" + value + "` - " + alias);
		}
	}

	protected List<String> getValues(String key) {
		return propertiesUtilService.getValues(properties, key);
	}

}