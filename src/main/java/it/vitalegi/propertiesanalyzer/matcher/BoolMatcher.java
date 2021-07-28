package it.vitalegi.propertiesanalyzer.matcher;

import java.util.Arrays;

public class BoolMatcher extends AbstractMatcher {

	public static final String[] PROPER_VALUES = new String[] { "true", "false" };

	@Override
	public String description() {
		return "Is boolean value. Recognized values (case-sensitive): " + Arrays.toString(PROPER_VALUES);
	}

	@Override
	public boolean matches(String value) {
		for (String v : PROPER_VALUES) {
			if (v.equals(value)) {
				return true;
			}
		}
		return false;
	}

}
