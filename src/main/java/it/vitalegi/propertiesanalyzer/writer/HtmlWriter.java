package it.vitalegi.propertiesanalyzer.writer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import it.vitalegi.propertiesanalyzer.util.MarkdownUtil;

public class HtmlWriter extends MarkdownWriter {

	protected OutputStream htmlOs;
	protected ByteArrayOutputStream markdownOs;

	public HtmlWriter(OutputStream os) {
		super();
		htmlOs = os;
		initMarkdownOs();
	}

	public HtmlWriter(String file) throws FileNotFoundException {
		super();
		htmlOs = new FileOutputStream(file);
		initMarkdownOs();
	}

	protected void initMarkdownOs() {
		markdownOs = new ByteArrayOutputStream();
		super.initWriter(markdownOs);
	}

	@Override
	public void close() throws IOException {
		super.close();
		toHtml();
	}

	protected void toHtml() throws IOException {
		ByteArrayInputStream markdownIs = new ByteArrayInputStream(markdownOs.toByteArray());
		MarkdownUtil.toHtml(markdownIs, htmlOs);
		markdownIs.close();
		htmlOs.close();
	}
}
