package it.vitalegi.propertiesanalyzer;

import it.vitalegi.propertiesanalyzer.writer.DocumentWriter;

public interface PropertiesProcessor {

	void process();

	void setWriter(DocumentWriter writer);
}