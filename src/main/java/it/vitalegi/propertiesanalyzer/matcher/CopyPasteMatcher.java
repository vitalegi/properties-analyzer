package it.vitalegi.propertiesanalyzer.matcher;

import java.util.List;

public class CopyPasteMatcher implements Matcher {

	@Override
	public String name() {
		return this.getClass().getSimpleName();
	}

	@Override
	public String description() {
		return "Are there 2 equal values and 2 not equals values?";
	}

	@Override
	public boolean trigger(List<String> values) {
		long distinctValues = values.stream()//
				.distinct().count();
		return 1 < distinctValues && distinctValues < values.size();
	}
}
