package it.vitalegi.propertiesanalyzer.matcher;

import java.util.List;

public interface Matcher {

	boolean matches(List<String> values);
}
