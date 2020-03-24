package it.vitalegi.propertiesanalyzer.writer;

import java.io.Closeable;
import java.util.List;

public interface DocumentWriter extends Closeable {

	void h1(String title);

	void h2(String title);

	void h3(String title);

	void newLine();

	void list(String value);

	void tableHeaders(List<String> titles);

	void tableRow(List<String> values);
}