package ch.ownz.s4m.sonos.xml;

import java.io.IOException;
import java.io.StringReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public final class XMLParserUtil {

	private static final Logger LOG = LoggerFactory.getLogger(XMLParserUtil.class);

	public static void parse(String xml, DefaultHandler contentHandler) {
		XMLReader reader;
		try {
			reader = XMLReaderFactory.createXMLReader();
			reader.setContentHandler(contentHandler);
			reader.parse(new InputSource(new StringReader(xml)));
		} catch (SAXException e) {
			LOG.error("Failed to parse XML", e);
		} catch (IOException e) {
			LOG.error("Failed to parse XML", e);
		}
	}
}
