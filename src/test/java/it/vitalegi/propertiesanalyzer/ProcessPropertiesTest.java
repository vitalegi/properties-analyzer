package it.vitalegi.propertiesanalyzer;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import it.vitalegi.propertiesanalyzer.matcher.Matcher;
import it.vitalegi.propertiesanalyzer.util.PropertiesUtil;
import it.vitalegi.propertiesanalyzer.writer.DocumentWriter;
import it.vitalegi.propertiesanalyzer.writer.HtmlWriter;
import it.vitalegi.propertiesanalyzer.writer.MarkdownWriter;

@SpringBootTest(classes = SpringTestConfig.class)
public class ProcessPropertiesTest {

	Logger log = LoggerFactory.getLogger(ProcessPropertiesTest.class);

	ProcessPropertiesImpl service;

	@BeforeEach
	void init() {
		service = new ProcessPropertiesImpl();
	}

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

		try (DocumentWriter writer = new MarkdownWriter("test.md")) {
			new ProcessPropertiesImpl(properties, Matcher.matchers(), writer).process();
		}

		try (DocumentWriter writer = new HtmlWriter("test.html")) {
			new ProcessPropertiesImpl(properties, Matcher.matchers(), writer).process();
		}
	}

	@Test
	void testProcessIncludesChanged() throws IOException {
		List<PropertiesAlias> properties = new ArrayList<>();
		properties.add(PropertiesUtil.createProperties("p1", //
				"key1", "5", //
				"key2", "txt"));
		properties.add(PropertiesUtil.createProperties("p2", //
				"key1", "6"));

		String out = process(properties, Matcher.matchers());
		log.info(">>" + out);
	}

	protected String process(List<PropertiesAlias> properties, List<Matcher> matchers) throws IOException {

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try (DocumentWriter writer = new MarkdownWriter(os)) {
			new ProcessPropertiesImpl(properties, Matcher.matchers(), writer).process();
		}
		return os.toString("UTF-8");
	}

	@Test
	void testGetKeys() {
		List<PropertiesAlias> properties = new ArrayList<>();
		properties.add(PropertiesUtil.createProperties("p1", "test.key1", "value1", "test.key2", "value2"));
		properties.add(PropertiesUtil.createProperties("p2", "test.key1", "value1", "test.key3", "value3"));
		List<String> keys = service.getKeys(properties);
		assertEquals(3, keys.size());
		assertTrue(keys.contains("test.key1"));
		assertTrue(keys.contains("test.key2"));
		assertTrue(keys.contains("test.key3"));
	}

	@Test
	void testGetValues() {
		List<PropertiesAlias> properties = new ArrayList<>();
		properties.add(PropertiesUtil.createProperties("p1", "test.key1", "value11", "test.key2", "value12"));
		properties.add(PropertiesUtil.createProperties("p2", "test.key1", "value21", "test.key3", "value32"));

		assertListEquals(Arrays.asList("value11", "value21"), service.getValues(properties, "test.key1"));
		assertListEquals(Arrays.asList("value12", null), service.getValues(properties, "test.key2"));
		assertListEquals(Arrays.asList(null, "value32"), service.getValues(properties, "test.key3"));
	}

	protected void assertListEquals(List<?> expected, List<?> actual) {
		assertArrayEquals(expected.toArray(), actual.toArray());
	}

}
