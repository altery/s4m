package ch.ownz.s4m.sonos.service.avtransport;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.teleal.cling.model.action.ActionInvocation;
import org.teleal.cling.model.meta.RemoteService;
import org.teleal.cling.model.types.UnsignedIntegerFourBytes;

import ch.ownz.s4m.action.NoResponseServiceAction;
import ch.ownz.s4m.sonos.model.Entry;
import ch.ownz.s4m.sonos.service.SonosService;

public class SetTransportUri extends NoResponseServiceAction {

	private static final Logger LOG = LoggerFactory.getLogger(SetTransportUri.class);

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

	private int instanceId;
	private final String uri;
	private String metadata;

	public SetTransportUri(SonosService service, String uri) {
		super(service);
		this.uri = uri;
	}

	public SetTransportUri(SonosService service, Entry entry) {
		super(service);
		this.uri = entry.getRes();
		this.metadata = compileMetadataString(entry);
	}

	public void setInstanceId(int id) {
		if (id < 0) {
			throw new IllegalArgumentException("Id must not be negative");
		}
		this.instanceId = id;
	}

	@Override
	protected String getActionName() {
		return "SetAVTransportURI";
	}

	@Override
	protected void fillInput(ActionInvocation<RemoteService> actionInvocation) {
		actionInvocation.setInput("InstanceID", new UnsignedIntegerFourBytes(this.instanceId));
		actionInvocation.setInput("CurrentURI", this.uri);
		actionInvocation.setInput("CurrentURIMetaData", this.metadata);
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
}
