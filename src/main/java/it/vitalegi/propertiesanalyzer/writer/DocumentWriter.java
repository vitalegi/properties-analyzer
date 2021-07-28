package it.vitalegi.propertiesanalyzer.writer;

import java.io.Closeable;

public interface DocumentWriter extends Closeable {

	void h1(String title);

	void h2(String title);

	void h3(String title);

	void newLine();

	void list(String value);

}