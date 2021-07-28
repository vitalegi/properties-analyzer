package it.vitalegi.propertiesanalyzer.matcher;

import it.vitalegi.propertiesanalyzer.util.StringUtil;

public class NotEmptyStringMatcher extends AbstractMatcher {
	@Override
	public String description() {
		return "Is not empty?";
	}

	@Override
	public boolean matches(String value) {
		if (StringUtil.isNullOrEmpty(value)) {
			return false;
		}
		if (StringUtil.isNullOrEmpty(value.trim())) {
			return false;
		}
		return true;
	}

}
