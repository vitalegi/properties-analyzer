package it.vitalegi.propertiesanalyzer;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	@Override
	public void run(ApplicationArguments args) throws Exception {
		String out;
		try {
			out = args.getOptionValues("o").get(0);
		} catch (Exception e) {
			log.error("Missing out files. use argument --o=filename");
			return;
		}

		List<String> aliases = args.getOptionValues("a");
		List<String> propertiesFiles = args.getOptionValues("f");

		if (aliases.size() != propertiesFiles.size()) {
			log.error("Aliases and properties files don't match");
		}

		List<PropertiesAlias> properties = new ArrayList<>();
		for (int i = 0; i < aliases.size(); i++) {
			PropertiesAlias props = new PropertiesAlias();
			props.setAlias(aliases.get(i));
			props.setProperties(PropertiesUtil.readFile(propertiesFiles.get(i)));
			properties.add(props);
		}

		try (DocumentWriter writer = new HtmlWriter(new FileOutputStream(out))) {
			new ProcessPropertiesImpl(properties, Matcher.matchers(), writer).process();
		}
	}
}
