package it.vitalegi.propertiesanalyzer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import it.vitalegi.propertiesanalyzer.matcher.Matcher;
import it.vitalegi.propertiesanalyzer.writer.DocumentWriter;

@Service
public class PropertiesAnalyzerFactory {

	@Autowired
	ApplicationContext context;

	public PropertiesAnalyzerImpl newInstance(DocumentWriter writer, List<PropertiesAlias> properties,
			List<Matcher> matchers) {

		PropertiesAnalyzerImpl instance = context.getBean(PropertiesAnalyzerImpl.class);

		instance.setMatchers(matchers);
		instance.setProperties(properties);
		instance.setWriter(writer);
		return instance;
	}
}
