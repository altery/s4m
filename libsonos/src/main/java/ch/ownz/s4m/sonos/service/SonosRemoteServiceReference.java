package ch.ownz.s4m.sonos.service;

import org.teleal.cling.model.meta.RemoteDevice;
import org.teleal.cling.model.meta.RemoteService;
import org.teleal.cling.model.types.UDAServiceId;

public class SonosRemoteServiceReference {

	private static final String RENDERING_CONTROL_SERVICE_ID = "RenderingControl";

	private static final String DEVICE_PROPERTIES_SERVICE_ID = "DeviceProperties";

	private static final String MUSIC_SERVICES_SERVICE_ID = "MusicServices";

	private static final String AV_TRANSPORT_SERVICE_ID = "AVTransport";

	private final String serviceId;

	private SonosRemoteServiceReference(String serviceId) {
		this.serviceId = serviceId;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof SonosRemoteServiceReference)) {
			return false;
		}
		return this.serviceId.equals(((SonosRemoteServiceReference) obj).serviceId);
	}

	public RemoteService lookupService(RemoteDevice device) {
		return device.findService(new UDAServiceId(this.serviceId));
	}

	public static SonosRemoteServiceReference getRenderingControlServiceReference() {
		return new SonosRemoteServiceReference(RENDERING_CONTROL_SERVICE_ID);
	}

	public static SonosRemoteServiceReference getDevicePropertiesServiceReference() {
		return new SonosRemoteServiceReference(DEVICE_PROPERTIES_SERVICE_ID);
	}

	public static SonosRemoteServiceReference getMusicServicesServiceReference() {
		return new SonosRemoteServiceReference(MUSIC_SERVICES_SERVICE_ID);
	}

	public static SonosRemoteServiceReference getAvTransportServiceReference() {
		return new SonosRemoteServiceReference(AV_TRANSPORT_SERVICE_ID);
	}

}
