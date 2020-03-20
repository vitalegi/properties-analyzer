package it.vitalegi.propertiesanalyzer.matcher;

import java.util.List;

public interface Matcher {

	boolean allMatches(List<String> values);

	boolean anyMatches(List<String> values);
}
