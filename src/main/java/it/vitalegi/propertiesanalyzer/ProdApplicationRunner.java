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

import it.vitalegi.propertiesanalyzer.matcher.AbsolutePathMatcher;
import it.vitalegi.propertiesanalyzer.matcher.BoolMatcher;
import it.vitalegi.propertiesanalyzer.matcher.FileExtensionMatcher;
import it.vitalegi.propertiesanalyzer.matcher.LongMatcher;
import it.vitalegi.propertiesanalyzer.matcher.NotEmptyStringMatcher;
import it.vitalegi.propertiesanalyzer.matcher.SingleMatcher;
import it.vitalegi.propertiesanalyzer.matcher.TrimWhitespaceMatcher;
import it.vitalegi.propertiesanalyzer.matcher.UrlMatcher;
import it.vitalegi.propertiesanalyzer.util.PropertiesUtil;
import it.vitalegi.propertiesanalyzer.writer.DocumentWriter;
import it.vitalegi.propertiesanalyzer.writer.HtmlWriter;

@Profile("prod")
@Component
public class ProdApplicationRunner implements ApplicationRunner {
	Logger log = LoggerFactory.getLogger(ProdApplicationRunner.class);

	@Autowired
	GetPropertiesServiceImpl analyzePropertiesService;

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

		List<SingleMatcher> matchers = Arrays.asList( //
				new AbsolutePathMatcher(), //
				new BoolMatcher(), //
				new FileExtensionMatcher(), //
				new LongMatcher(), //
				new TrimWhitespaceMatcher(), //
				new UrlMatcher(), //
				new NotEmptyStringMatcher());

		try (DocumentWriter writer = new HtmlWriter(new FileOutputStream(out))) {
			new ProcessPropertiesImpl(analyzePropertiesService, properties, matchers, writer).process();
		}
	}
}
