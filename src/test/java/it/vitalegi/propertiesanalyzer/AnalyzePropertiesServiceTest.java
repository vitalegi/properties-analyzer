package it.vitalegi.propertiesanalyzer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import it.vitalegi.propertiesanalyzer.matcher.AbsolutePathMatcher;
import it.vitalegi.propertiesanalyzer.matcher.AndMatcher;
import it.vitalegi.propertiesanalyzer.matcher.BoolMatcher;
import it.vitalegi.propertiesanalyzer.matcher.EqualMatcher;
import it.vitalegi.propertiesanalyzer.matcher.FileExtensionMatcher;
import it.vitalegi.propertiesanalyzer.matcher.LongMatcher;
import it.vitalegi.propertiesanalyzer.matcher.Matcher;
import it.vitalegi.propertiesanalyzer.matcher.NotMatcher;
import it.vitalegi.propertiesanalyzer.matcher.OrMatcher;
import it.vitalegi.propertiesanalyzer.matcher.SingleMatcher;
import it.vitalegi.propertiesanalyzer.matcher.TrimWhitespaceMatcher;
import it.vitalegi.propertiesanalyzer.matcher.UrlMatcher;
import it.vitalegi.propertiesanalyzer.util.PropertiesUtil;

@SpringBootTest(classes = SpringTestConfig.class)
public class AnalyzePropertiesServiceTest {

	@Autowired
	AnalyzePropertiesServiceImpl service;

	@Test
	void testAnalyze() {
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

		Matcher filterBy = new NotMatcher(new EqualMatcher());
		List<SingleMatcher> showMatchers = Arrays.asList( //
				new AbsolutePathMatcher(), //
				new BoolMatcher(), //
				new FileExtensionMatcher(), //
				new LongMatcher(), //
				new TrimWhitespaceMatcher(), //
				new UrlMatcher());

		new AnalyzeToMarkdownImpl(service, properties, filterBy, showMatchers, new PrintWriter(System.out)).process();
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

	@Test
	void testAllEqualsIfEquals() {
		List<String> values = Arrays.asList("value", "value");
		assertTrue(new EqualMatcher().matches(values));
	}

	@Test
	void testAllEqualsIfNotEquals() {
		List<String> values = Arrays.asList("value", "different value");
		assertFalse(new EqualMatcher().matches(values));
	}

	@Test
	void testUrlsIfUrl() {
		assertTrue(new UrlMatcher().matches("https://localhost/aaa?q=123"));
		assertTrue(new UrlMatcher().matches("https://192.0.0.1/aaa"));
		assertTrue(new UrlMatcher().matches("https://google.com/"));
	}

	@Test
	void testUrlsIfNotUrl() {
		assertFalse(new UrlMatcher().matches("test"));
	}

	@Test
	void testLongIfLongs() {
		assertTrue(new LongMatcher().matches("11"));
	}

	@Test
	void testLongIfNotLongs() {
		assertFalse(new LongMatcher().matches("5.0"));
	}

	@Test
	void testBooleanIfBoolean() {
		assertTrue(new BoolMatcher().matches("true"));
		assertTrue(new BoolMatcher().matches("false"));
	}

	@Test
	void testBooleanIfNotBoolean() {
		assertFalse(new LongMatcher().matches("TRUE"));
		assertFalse(new LongMatcher().matches("aaaaaa"));
	}

	@Test
	void testFilePathIfUnixPath() {
		assertTrue(new AbsolutePathMatcher().matches("/path/to/file.txt"));
	}

	@Test
	void testFilePathIfUnixRelativePath() {
		assertFalse(new AbsolutePathMatcher().matches("path/to/file.txt"));
	}

	@Test
	void testFilePathIfWindowsPath() {
		assertTrue(new AbsolutePathMatcher().matches("C:\\path\\to\\file.txt"));
	}

	@Test
	void testFilePathIfWindowsRelativePath() {
		assertFalse(new AbsolutePathMatcher().matches("path\\to\\file.txt"));
	}

	@Test
	void testFilePathIfNotPath() {
		assertFalse(new AbsolutePathMatcher().matches("aaaa"));
	}

	@Test
	void testFileExtensionIfExtension() {
		assertTrue(new FileExtensionMatcher().matches("a.txt"));
	}

	@Test
	void testFileExtensionIfNoExtension() {
		assertFalse(new FileExtensionMatcher().matches("aaaa"));
	}

	@Test
	void testFileExtensionIfNonValidChar() {
		assertFalse(new FileExtensionMatcher().matches("aa.txt;"));
	}

	@Test
	void testFileExtensionIfNull() {
		String str = null;
		assertFalse(new FileExtensionMatcher().matches(str));
	}

	@Test
	void testTrimWhitespaceIfWhitespacePrefix() {
		assertTrue(new TrimWhitespaceMatcher().matches(" aaa"));
	}

	@Test
	void testTrimWhitespaceIfWhitespaceSuffix() {
		assertTrue(new TrimWhitespaceMatcher().matches("aaa "));
	}

	@Test
	void testTrimWhitespaceIfNoWhitespace() {
		assertFalse(new TrimWhitespaceMatcher().matches("aaa"));
	}

	@Test
	void testNotMatcherIfTrue() {
		assertFalse(new NotMatcher(dummyMatcher(true)).matches(Arrays.asList("aaa")));
	}

	@Test
	void testNotMatcherIfFalse() {
		assertTrue(new NotMatcher(dummyMatcher(false)).matches(Arrays.asList("aaa")));
	}

	@Test
	void testOrMatcher() {
		assertTrue(new OrMatcher(dummyMatchers(true, true)).matches(Arrays.asList("aaa")));
		assertTrue(new OrMatcher(dummyMatchers(true, false)).matches(Arrays.asList("aaa")));
		assertTrue(new OrMatcher(dummyMatchers(false, true)).matches(Arrays.asList("aaa")));
		assertFalse(new OrMatcher(dummyMatchers(false, false)).matches(Arrays.asList("aaa")));
	}

	@Test
	void testAndMatcher() {
		assertTrue(new AndMatcher(dummyMatchers(true, true)).matches(Arrays.asList("aaa")));
		assertFalse(new AndMatcher(dummyMatchers(true, false)).matches(Arrays.asList("aaa")));
		assertFalse(new AndMatcher(dummyMatchers(false, true)).matches(Arrays.asList("aaa")));
		assertFalse(new AndMatcher(dummyMatchers(false, false)).matches(Arrays.asList("aaa")));
	}

	protected Matcher dummyMatcher(boolean output) {
		Matcher matcher = Mockito.mock(Matcher.class);
		Mockito.when(matcher.matches(Mockito.any())).thenReturn(output);
		return matcher;
	}

	protected List<Matcher> dummyMatchers(boolean... outputs) {
		List<Matcher> matchers = new ArrayList<>();
		for (boolean out : outputs) {
			matchers.add(dummyMatcher(out));
		}
		return matchers;
	}
}
