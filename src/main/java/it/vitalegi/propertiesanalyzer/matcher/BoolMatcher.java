package it.vitalegi.propertiesanalyzer.matcher;

public class BoolMatcher extends AbstractMatcher {

	public static final String[] PROPER_VALUES = new String[] { "true", "false" };

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
