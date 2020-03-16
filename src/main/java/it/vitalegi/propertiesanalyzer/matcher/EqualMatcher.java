package it.vitalegi.propertiesanalyzer.matcher;

import java.util.List;

import it.vitalegi.propertiesanalyzer.util.ListUtil;

public class EqualMatcher implements Matcher {

	@Override
	public boolean matches(List<String> values) {
		if (ListUtil.isEmpty(values)) {
			return true;
		}
		String base = values.get(0);
		for (String value : values) {
			if (!matches(value, base)) {
				return false;
			}
		}
		return true;
	}

	protected boolean matches(String value, String compareTo) {
		return compareTo.equals(value);
	}

}
