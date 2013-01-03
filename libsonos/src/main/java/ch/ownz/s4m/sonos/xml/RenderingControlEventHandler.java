package ch.ownz.s4m.sonos.xml;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ch.ownz.s4m.sonos.service.RenderingControlService;

/**
 * Parses events of the {@link RenderingControlService}.
 */
public class RenderingControlEventHandler extends DefaultHandler {

	/**
	 * The Enum RenderingControlEventType.
	 */
	public static enum RenderingControlEventType {

		/** The volume master. */
		VOLUME_MASTER,

		/** The volume rf. */
		VOLUME_RF,

		/** The volume lf. */
		VOLUME_LF,

		/** The mute master. */
		MUTE_MASTER,

		/** The mute lf. */
		MUTE_LF,

		/** The mute rf. */
		MUTE_RF,

		/** The bass. */
		BASS,

		/** The treble. */
		TREBLE,

		/** The loudness. */
		LOUDNESS,

		/** The output fixed. */
		OUTPUT_FIXED,

		/** The preset name. */
		PRESET_NAME;
	}

	/** The changes. */
	private final Map<RenderingControlEventHandler.RenderingControlEventType, String> changes = new HashMap<RenderingControlEventHandler.RenderingControlEventType, String>();

	/** The get preset name. */
	private boolean getPresetName = false;

	/** The preset name. */
	private String presetName;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (this.getPresetName) {
			this.presetName = new String(ch, start, length);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (this.getPresetName) {
			this.getPresetName = false;
			this.changes.put(RenderingControlEventType.PRESET_NAME, this.presetName);
		}
	}

	/**
	 * Gets the changes.
	 * 
	 * @return the changes
	 */
	public Map<RenderingControlEventType, String> getChanges() {
		return this.changes;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
	 *      java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		if ("Volume".equals(qName)) {
			if ("Master".equals(atts.getValue("channel"))) {
				this.changes.put(RenderingControlEventType.VOLUME_MASTER, atts.getValue("val"));
			} else if ("LF".equals(atts.getValue("channel"))) {
				this.changes.put(RenderingControlEventType.VOLUME_LF, atts.getValue("val"));
			} else if ("RF".equals(atts.getValue("channel"))) {
				this.changes.put(RenderingControlEventType.VOLUME_RF, atts.getValue("val"));
			} // ignore other channels
		} else if ("Mute".equals(qName)) {
			if ("Master".equals(atts.getValue("channel"))) {
				this.changes.put(RenderingControlEventType.MUTE_MASTER, atts.getValue("val"));
			} else if ("LF".equals(atts.getValue("channel"))) {
				this.changes.put(RenderingControlEventType.MUTE_LF, atts.getValue("val"));
			} else if ("RF".equals(atts.getValue("channel"))) {
				this.changes.put(RenderingControlEventType.MUTE_RF, atts.getValue("val"));
			} // ignore other channels
		} else if ("Bass".equals(qName)) {
			this.changes.put(RenderingControlEventType.BASS, atts.getValue("val"));
		} else if ("Treble".equals(qName)) {
			this.changes.put(RenderingControlEventType.TREBLE, atts.getValue("val"));
		} else if ("Loudness".equals(qName)) {
			if ("Master".equals(atts.getValue("channel"))) {
				this.changes.put(RenderingControlEventType.LOUDNESS, atts.getValue("val"));
			} // ignore other channels
		} else if ("OutputFixed".equals(qName)) {
			this.changes.put(RenderingControlEventType.OUTPUT_FIXED, atts.getValue("val"));
		} else if ("PresetNameList".equals(qName)) {
			this.getPresetName = true;
		}
	}

}