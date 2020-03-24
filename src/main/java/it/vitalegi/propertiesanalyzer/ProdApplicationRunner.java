package it.vitalegi.propertiesanalyzer;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import it.vitalegi.propertiesanalyzer.matcher.Matcher;
import it.vitalegi.propertiesanalyzer.util.PropertiesUtil;
import it.vitalegi.propertiesanalyzer.writer.DocumentWriter;
import it.vitalegi.propertiesanalyzer.writer.HtmlWriter;

@Profile("prod")
@Component
public class ProdApplicationRunner implements ApplicationRunner {
	Logger log = LoggerFactory.getLogger(ProdApplicationRunner.class);

	@Autowired
	PropertiesProcessorFactory factory;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		String out;
		String mode;
		List<String> aliases;
		List<String> propertiesFiles;

		try {
			out = getOutFile(args);
			mode = getMode(args);

			aliases = args.getOptionValues("a");
			propertiesFiles = args.getOptionValues("f");

			if (aliases.size() != propertiesFiles.size()) {
				throw new InvalidInputParameterException("Aliases and properties files don't match");
			}
		} catch (InvalidInputParameterException e) {
			log.error("Invalid input provided: {}", e.getMessage());
			return;
		}

		List<PropertiesAlias> properties = new ArrayList<>();
		for (int i = 0; i < aliases.size(); i++) {
			PropertiesAlias props = new PropertiesAlias();
			props.setAlias(aliases.get(i));
			props.setProperties(PropertiesUtil.readFile(propertiesFiles.get(i)));
			properties.add(props);
		}
		PropertiesProcessor service = factory.newInstance(mode, properties, Matcher.matchers());

		try (DocumentWriter writer = new HtmlWriter(new FileOutputStream(out))) {

			service.setWriter(writer);
			service.process();
		}
	}

	protected String getMode(ApplicationArguments args) {

		List<String> acceptedValues = Arrays.asList(PropertiesAnalyzerImpl.MODE, PropertiesComparatorImpl.MODE);
		String mode;
		try {
			mode = args.getOptionValues("m").get(0);
		} catch (Exception e) {
			throw new InvalidInputParameterException("Missing mode. use argument --m=mode, values: " + acceptedValues);
		}
		if (PropertiesAnalyzerImpl.MODE.equals(mode) || PropertiesComparatorImpl.MODE.equals(mode)) {
			return mode;
		}
		throw new InvalidInputParameterException("Unrecognized mode. use argument --m=mode, values: " + acceptedValues);
	}

	protected String getOutFile(ApplicationArguments args) {
		try {
			return args.getOptionValues("o").get(0);
		} catch (Exception e) {
			throw new InvalidInputParameterException("Missing out files. use argument --o=filename");
		}
	}
}
