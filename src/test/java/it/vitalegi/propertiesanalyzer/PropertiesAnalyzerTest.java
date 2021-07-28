package it.vitalegi.propertiesanalyzer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import it.vitalegi.propertiesanalyzer.matcher.Matcher;
import it.vitalegi.propertiesanalyzer.util.PropertiesUtil;
import it.vitalegi.propertiesanalyzer.writer.DocumentWriter;
import it.vitalegi.propertiesanalyzer.writer.HtmlWriter;
import it.vitalegi.propertiesanalyzer.writer.MarkdownWriter;

@SpringBootTest(classes = SpringTestConfig.class)
public class PropertiesAnalyzerTest {

	Logger log = LoggerFactory.getLogger(PropertiesAnalyzerTest.class);

	@Autowired
	PropertiesAnalyzerFactory factory;

	@Test
	void testAnalyze() throws IOException {
		List<PropertiesAlias> properties = new ArrayList<>();
		properties.add(PropertiesUtil.createProperties("p1", //
				"key1", "value1", //
				"key2", "value2", //
				"key3", "value3", //
				"key4[0]", "value4[0]", //
				"key4[1]", "value4[1]", //
				"key5", "value5", //
				"key.bool.true", "true", //
				"key.bool.false", "false", //
				"key.file.extension", "hello.txt", //
				"key.long", "10", //
				"key.trim.whitespaces", "true ", //
				"key.url", "https://google.com"));
		properties.add(PropertiesUtil.createProperties("p2", //
				"key1", "value1", //
				"key3", "value3"));
		properties.add(PropertiesUtil.createProperties("p3", "key1", "value1", "key3", "value3"));

		PropertiesAnalyzerImpl service;
		try (DocumentWriter writer = new MarkdownWriter("test.md")) {
			service = factory.newInstance(writer, properties, Matcher.matchers());
			service.process();
		}

		try (DocumentWriter writer = new HtmlWriter("test.html")) {
			service = factory.newInstance(writer, properties, Matcher.matchers());
			service.process();
		}
	}
}
