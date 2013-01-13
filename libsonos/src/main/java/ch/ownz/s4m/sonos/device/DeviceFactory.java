package ch.ownz.s4m.sonos.device;

import org.teleal.cling.controlpoint.ControlPoint;
import org.teleal.cling.model.meta.RemoteDevice;
import org.teleal.cling.model.meta.RemoteService;
import org.teleal.cling.model.types.DeviceType;
import org.teleal.cling.model.types.UDADeviceType;

import ch.ownz.s4m.sonos.service.SonosRemoteServiceReference;

public class DeviceFactory {

	private static final String MEDIA_RENDERER_DEVICE_ID = "MediaRenderer";

	private static final String MEDIA_SERVER_DEVICE_ID = "MediaServer";

	private final ControlPoint controlPoint;

	public DeviceFactory(ControlPoint controlPoint) {
		this.controlPoint = controlPoint;
	}

	public ZonePlayerDevice createZonePlayerDevice(RemoteDevice device) {
		RemoteService musicServicesService = SonosRemoteServiceReference.getMusicServicesServiceReference()
				.lookupService(device);
		if (musicServicesService != null) {
			// Only sonos devices that provide the music services service are
			// zone players (TODO: confirm this assumption)
			ZonePlayerDevice zonePlayerDevice = new ZonePlayerDevice();
			zonePlayerDevice.init(device, this.controlPoint);
			return zonePlayerDevice;
		}
		return null;
	}

	public SonosHardwareDevice createSonosHardwareDevice(RemoteDevice device) {
		SonosHardwareDevice sonosHardwareDevice = new SonosHardwareDevice();
		sonosHardwareDevice.init(device, this.controlPoint);
		return sonosHardwareDevice;

	}

	public MediaRendererDevice createMediaRendererDevice(ZonePlayerDevice player) {
		RemoteDevice parentDevice = player.getUpnpDevice();
		RemoteDevice embeddedDevice = findEmbeddedDevices(parentDevice, MEDIA_RENDERER_DEVICE_ID);
		if (embeddedDevice != null) {
			MediaRendererDevice mediaRendererDevice = new MediaRendererDevice();
			mediaRendererDevice.init(embeddedDevice, this.controlPoint);
			return mediaRendererDevice;
		}
		return null;
	}

	public MediaServerDevice createMediaServerDevice(ZonePlayerDevice player) {
		RemoteDevice parentDevice = player.getUpnpDevice();
		RemoteDevice embeddedDevice = findEmbeddedDevices(parentDevice, MEDIA_SERVER_DEVICE_ID);
		if (embeddedDevice != null) {
			MediaServerDevice mediaServerDevice = new MediaServerDevice();
			mediaServerDevice.init(embeddedDevice, this.controlPoint);
			return mediaServerDevice;
		}
		return null;
	}

	protected RemoteDevice findEmbeddedDevices(RemoteDevice parentDevice, String embeddedDeviceType) {
		RemoteDevice[] embeddedDevices = parentDevice.getEmbeddedDevices();
		UDADeviceType udaDeviceType = new UDADeviceType(embeddedDeviceType);
		for (RemoteDevice embeddedDevice : embeddedDevices) {
			DeviceType type = embeddedDevice.getType();
			if (type.equals(udaDeviceType)) {
				return embeddedDevice;
			}
		}
		return null;
	}

}
