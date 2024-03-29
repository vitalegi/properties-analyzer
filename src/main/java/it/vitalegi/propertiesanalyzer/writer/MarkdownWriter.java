package it.vitalegi.propertiesanalyzer.writer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class MarkdownWriter implements DocumentWriter {

	PrintWriter writer;

	protected MarkdownWriter() {
	}

	public MarkdownWriter(OutputStream os) {
		initWriter(os);
	}

	public MarkdownWriter(String file) throws FileNotFoundException {
		initWriter(file);
	}

	protected void initWriter(OutputStream os) {
		this.writer = new PrintWriter(os);
	}

	protected void initWriter(String file) throws FileNotFoundException {
		this.writer = new PrintWriter(file);
	}

	@Override
	public void h1(String title) {
		writer.append("# " + title + "\n\n");
	}

	@Override
	public void h2(String title) {
		writer.append("## " + title + "\n\n");
	}

	@Override
	public void h3(String title) {
		writer.append("### " + title + "\n\n");
	}

	@Override
	public void newLine() {
		writer.append("\n");
	}

	@Override
	public void list(String value) {
		writer.append("- " + value + "\n");
	}

	@Override
	public void close() throws IOException {
		writer.close();
	}
}
