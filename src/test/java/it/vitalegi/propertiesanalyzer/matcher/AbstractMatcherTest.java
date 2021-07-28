package it.vitalegi.propertiesanalyzer.matcher;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import it.vitalegi.propertiesanalyzer.SpringTestConfig;

@SpringBootTest(classes = SpringTestConfig.class)
public class AbstractMatcherTest {

	private static class AbstractMatcherWrapper extends AbstractMatcher {

		private boolean anyMatches;
		private boolean anyNotMatches;

		public AbstractMatcherWrapper(boolean anyMatches, boolean anyNotMatches) {
			super();
			this.anyMatches = anyMatches;
			this.anyNotMatches = anyNotMatches;
		}

		@Override
		public String description() {
			return null;
		}

		@Override
		protected boolean matches(String value) {
			return false;
		}

		@Override
		protected boolean anyMatches(List<String> values) {
			return anyMatches;
		}

		@Override
		protected boolean anyNotMatches(List<String> values) {
			return anyNotMatches;
		}

	}

	@Test
	void atLeastOneMatchesAndOneNotMatchesShouldTrigger() {
		AbstractMatcher matcher = new AbstractMatcherWrapper(true, true);

		assertTrue(matcher.trigger(Arrays.asList("1")));
	}

	@Test
	void allMatchesShouldNotTrigger() {
		AbstractMatcher matcher = new AbstractMatcherWrapper(true, false);

		assertFalse(matcher.trigger(Arrays.asList("1")));
	}

	@Test
	void allNotMatchesShouldNotTrigger() {
		AbstractMatcher matcher = new AbstractMatcherWrapper(false, true);

		assertFalse(matcher.trigger(Arrays.asList("1")));
	}
}
