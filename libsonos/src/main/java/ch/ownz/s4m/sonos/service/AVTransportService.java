package ch.ownz.s4m.sonos.service;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.teleal.cling.model.gena.GENASubscription;
import org.teleal.cling.model.meta.RemoteService;

import ch.ownz.s4m.sonos.actionexecution.Message;
import ch.ownz.s4m.sonos.model.Entry;
import ch.ownz.s4m.sonos.model.PlayMode;

public class AVTransportService extends SonosService {

	private static final Logger LOG = LoggerFactory.getLogger(AVTransportService.class);

	private static final String SET_AV_TRANSPORT_URI_ACTION = "SetAVTransportURI";

	/**
	 * The format for a metadata tag: 0: id 1: parent id 2: title 3: upnp:class.
	 */
	private static final MessageFormat METADATA_FORMAT = new MessageFormat(
			"<DIDL-Lite xmlns:dc=\"http://purl.org/dc/elements/1.1/\" "
					+ "xmlns:upnp=\"urn:schemas-upnp-org:metadata-1-0/upnp/\" "
					+ "xmlns:r=\"urn:schemas-rinconnetworks-com:metadata-1-0/\" "
					+ "xmlns=\"urn:schemas-upnp-org:metadata-1-0/DIDL-Lite/\">"
					+ "<item id=\"{0}\" parentID=\"{1}\" restricted=\"true\">" + "<dc:title>{2}</dc:title>"
					+ "<upnp:class>{3}</upnp:class>"
					+ "<desc id=\"cdudn\" nameSpace=\"urn:schemas-rinconnetworks-com:metadata-1-0/\">"
					+ "RINCON_AssociatedZPUDN</desc>" + "</item></DIDL-Lite>");

	private static final String PLAY_ACTION = "Play";

	private static final String STOP_ACTION = "Stop";

	private static final String PAUSE_ACTION = "Pause";

	private static final String SET_PLAY_MODE_ACTION = "SetPlayMode";

	/**
	 * Sets the input to the given entry without modifing the queue.
	 * 
	 * @param entry
	 *            the entry
	 */
	public void setAvTransportUri(Entry entry) {
		Message message = createMessage(SET_AV_TRANSPORT_URI_ACTION);
		message.set("InstanceID", "0");
		message.set("CurrentURI", entry.getRes());
		String metadata = compileMetadataString(entry);
		message.set("CurrentURIMetaData", metadata);
		getOwningDevice().execute(message);
	}

	/**
	 * Starts the playback of the current transport uri.
	 */
	public void play() {
		Message message = createMessage(PLAY_ACTION);
		message.set("InstanceID", "0");
		message.set("Speed", "1");
		getOwningDevice().execute(message);
	}

	/**
	 * Stops the playback
	 */
	public void stop() {
		Message message = createMessage(STOP_ACTION);
		message.set("InstanceID", "0");
		getOwningDevice().execute(message);
	}

	/**
	 * Pauses the playback
	 */
	public void pause() {
		Message message = createMessage(PAUSE_ACTION);
		message.set("InstanceID", "0");
		getOwningDevice().execute(message);
	}

	/**
	 * Sets the play mode.
	 * 
	 * @param mode
	 */
	public void setPlayMode(PlayMode mode) {
		Message message = createMessage(SET_PLAY_MODE_ACTION);
		message.set("InstanceID", "0");
		message.set("NewPlayMode", mode.toString());
		getOwningDevice().execute(message);
	}

	@Override
	protected void handleServiceEvent(GENASubscription<RemoteService> subscription) {
	}

	private static String compileMetadataString(final Entry entry) {
		String upnpClass = entry.getUpnpClass();
		if (upnpClass.startsWith("object.container")) {
			upnpClass = "object.container";
		}
		String metadata = METADATA_FORMAT.format(new Object[] { entry.getId(), entry.getParentId(), entry.getTitle(),
				upnpClass });
		if (LOG.isDebugEnabled()) {
			LOG.debug("Created metadata: " + metadata);
		}
		return metadata;
	}

	@Override
	protected SonosRemoteServiceReference getRemoteServiceReference() {
		return SonosRemoteServiceReference.getAvTransportServiceReference();
	}

}
