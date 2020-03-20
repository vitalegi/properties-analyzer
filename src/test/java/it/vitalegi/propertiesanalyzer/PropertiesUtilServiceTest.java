package it.vitalegi.propertiesanalyzer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import it.vitalegi.propertiesanalyzer.matcher.Matcher;
import it.vitalegi.propertiesanalyzer.util.PropertiesUtil;

@SpringBootTest(classes = SpringTestConfig.class)
public class PropertiesUtilServiceTest {

	Logger log = LoggerFactory.getLogger(PropertiesUtilServiceTest.class);

	@Autowired
	PropertiesUtilServiceImpl service;

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
	void hasMismatchIfBothMatchMismatch() {
		Matcher matcher = Mockito.mock(Matcher.class);
		Mockito.when(matcher.anyMatches(Mockito.any())).thenReturn(true);
		Mockito.when(matcher.anyNotMatches(Mockito.any())).thenReturn(true);

		assertTrue(service.hasMismatch(matcher, Arrays.asList("1")));
	}

	@Test
	void hasMismatchIfAllMatches() {
		Matcher matcher = Mockito.mock(Matcher.class);
		Mockito.when(matcher.anyMatches(Mockito.any())).thenReturn(true);
		Mockito.when(matcher.anyNotMatches(Mockito.any())).thenReturn(false);

		assertFalse(service.hasMismatch(matcher, Arrays.asList("1")));
	}

	@Test
	void hasMismatchIfNoMatch() {
		Matcher matcher = Mockito.mock(Matcher.class);
		Mockito.when(matcher.anyMatches(Mockito.any())).thenReturn(false);
		Mockito.when(matcher.anyNotMatches(Mockito.any())).thenReturn(true);

		assertFalse(service.hasMismatch(matcher, Arrays.asList("1")));
	}
}