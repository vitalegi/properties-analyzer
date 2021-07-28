package it.vitalegi.propertiesanalyzer.matcher;

import it.vitalegi.propertiesanalyzer.util.StringUtil;

public class FileExtensionMatcher extends AbstractMatcher {
	@Override
	public String description() {
		return "Has a file extension?";
	}

	@Override
	public boolean matches(String value) {
		String extension = extension(value);
		if (StringUtil.isNullOrEmpty(extension)) {
			return false;
		}
		return extension.matches("[a-zA-Z0-9_]+");
	}

	protected String extension(String value) {
		if (StringUtil.isNullOrEmpty(value)) {
			return "";
		}
		int index = value.lastIndexOf(".");
		if (index > 0) {
			return value.substring(index + 1);
		}
		return "";
	}
}
