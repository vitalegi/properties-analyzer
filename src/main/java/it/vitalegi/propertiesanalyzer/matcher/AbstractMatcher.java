package it.vitalegi.propertiesanalyzer.matcher;

import java.util.List;

import it.vitalegi.propertiesanalyzer.util.ListUtil;

public abstract class AbstractMatcher implements Matcher {

	@Override
	public String name() {
		return this.getClass().getSimpleName();
	}

	@Override
	public boolean allMatches(List<String> values) {
		if (ListUtil.isEmpty(values)) {
			// return true;
		}
		for (String value : values) {
			if (!matches(value)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean anyMatches(List<String> values) {
		if (ListUtil.isEmpty(values)) {
			// return true;
		}
		for (String value : values) {
			if (matches(value)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean anyNotMatches(List<String> values) {
		if (ListUtil.isEmpty(values)) {
			// return true;
		}
		for (String value : values) {
			if (!matches(value)) {
				return true;
			}
		}
		return false;
	}
}
