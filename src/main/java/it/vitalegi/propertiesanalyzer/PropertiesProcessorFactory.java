package it.vitalegi.propertiesanalyzer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import it.vitalegi.propertiesanalyzer.matcher.Matcher;

@Service
public class PropertiesProcessorFactory {

	@Autowired
	ApplicationContext context;

	public PropertiesProcessor newInstance(String mode, List<PropertiesAlias> properties, List<Matcher> matchers) {

		if (PropertiesComparatorImpl.MODE.equals(mode)) {
			return newComparatorInstance(properties, matchers);
		}
		if (PropertiesAnalyzerImpl.MODE.equals(mode)) {
			return newAnalyzerInstance(properties, matchers);
		}
		return newAnalyzerInstance(properties, matchers);
	}

	public PropertiesProcessor newAnalyzerInstance(List<PropertiesAlias> properties, List<Matcher> matchers) {

		PropertiesAnalyzerImpl instance = context.getBean(PropertiesAnalyzerImpl.class);

		instance.setMatchers(matchers);
		instance.setProperties(properties);
		return instance;
	}

	public PropertiesProcessor newComparatorInstance(List<PropertiesAlias> properties, List<Matcher> matchers) {

		PropertiesComparatorImpl instance = context.getBean(PropertiesComparatorImpl.class);

		instance.setProperties(properties);
		instance.setMatchers(matchers);
		return instance;
	}
}
