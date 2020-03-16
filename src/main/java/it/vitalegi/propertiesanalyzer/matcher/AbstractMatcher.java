package it.vitalegi.propertiesanalyzer.matcher;

import java.util.List;

import it.vitalegi.propertiesanalyzer.util.ListUtil;

public abstract class AbstractMatcher implements Matcher, SingleMatcher {

	@Override
	public boolean matches(List<String> values) {
		if (ListUtil.isEmpty(values)) {
			return true;
		}
		for (String value : values) {
			if (!matches(value)) {
				return false;
			}
		}
		return true;
	}
}
