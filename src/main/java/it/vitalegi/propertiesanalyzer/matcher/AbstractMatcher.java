package it.vitalegi.propertiesanalyzer.matcher;

import java.util.List;

public abstract class AbstractMatcher implements Matcher {

	@Override
	public String name() {
		return this.getClass().getSimpleName();
	}

	@Override
	public boolean trigger(List<String> values) {
		return anyMatches(values) && anyNotMatches(values);
	}

	protected boolean anyMatches(List<String> values) {
		for (String value : values) {
			if (matches(value)) {
				return true;
			}
		}
		return false;
	}

	protected boolean anyNotMatches(List<String> values) {
		for (String value : values) {
			if (!matches(value)) {
				return true;
			}
		}
		return false;
	}

	protected abstract boolean matches(String value);
}
