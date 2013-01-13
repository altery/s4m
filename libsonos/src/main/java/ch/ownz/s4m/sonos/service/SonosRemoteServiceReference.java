package ch.ownz.s4m.sonos.service;

import org.teleal.cling.model.meta.RemoteDevice;
import org.teleal.cling.model.meta.RemoteService;
import org.teleal.cling.model.types.UDAServiceId;

public class SonosRemoteServiceReference {

	private static final String CONTENT_DIRECTORY_SERVICE_ID = "ContentDirectory";

	private static final String CONNECTION_MANAGER_SERVICE_ID = "ConnectionManager";

	private static final String ALARM_CLOCK_SERVICE_ID = "AlarmClock";

	private static final String AUDIO_IN_SERVICE_ID = "AudioIn";

	private static final String SYSTEM_PROPERTIES_SERVICE_ID = "SystemProperties";

	private static final String ZONE_GROUP_TOPOLOGY_SERVICE_ID = "ZoneGroupTopology";

	private static final String RENDERING_CONTROL_SERVICE_ID = "RenderingControl";

	private static final String DEVICE_PROPERTIES_SERVICE_ID = "DeviceProperties";

	private static final String MUSIC_SERVICES_SERVICE_ID = "MusicServices";

	private static final String AV_TRANSPORT_SERVICE_ID = "AVTransport";

	private static final String GROUP_MANAGEMENT_SERVICE_ID = "GroupManagement";

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

	public static SonosRemoteServiceReference getGroupManagementServiceReference() {
		return new SonosRemoteServiceReference(GROUP_MANAGEMENT_SERVICE_ID);
	}

	public static SonosRemoteServiceReference getZoneGroupTopologyServiceReference() {
		return new SonosRemoteServiceReference(ZONE_GROUP_TOPOLOGY_SERVICE_ID);
	}

	public static SonosRemoteServiceReference getSystemPropertiesServiceReference() {
		return new SonosRemoteServiceReference(SYSTEM_PROPERTIES_SERVICE_ID);
	}

	public static SonosRemoteServiceReference getAudioInServiceReference() {
		return new SonosRemoteServiceReference(AUDIO_IN_SERVICE_ID);
	}

	public static SonosRemoteServiceReference getAlarmClockServiceReference() {
		return new SonosRemoteServiceReference(ALARM_CLOCK_SERVICE_ID);
	}

	public static SonosRemoteServiceReference getConnectionManagerServiceReference() {
		return new SonosRemoteServiceReference(CONNECTION_MANAGER_SERVICE_ID);
	}

	public static SonosRemoteServiceReference getContentDirectoryServiceReference() {
		return new SonosRemoteServiceReference(CONTENT_DIRECTORY_SERVICE_ID);
	}

}
