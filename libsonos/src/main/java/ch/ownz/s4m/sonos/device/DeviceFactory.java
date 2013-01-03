package ch.ownz.s4m.sonos.device;

import org.teleal.cling.controlpoint.ControlPoint;
import org.teleal.cling.model.meta.RemoteDevice;
import org.teleal.cling.model.meta.RemoteService;
import org.teleal.cling.model.types.DeviceType;
import org.teleal.cling.model.types.UDADeviceType;

import ch.ownz.s4m.sonos.service.SonosRemoteServiceReference;

public class DeviceFactory {

	private static final String MEDIA_RENDERER_DEVICE_ID = "MediaRenderer";

	private final ControlPoint controlPoint;

	public DeviceFactory(ControlPoint controlPoint) {
		this.controlPoint = controlPoint;
	}

	public ZonePlayerDevice createZonePlayerDevice(RemoteDevice device) {
		RemoteService musicServicesService = SonosRemoteServiceReference.getMusicServicesServiceReference()
				.lookupService(device);
		if (musicServicesService != null) {
			// Only sonos devices that provide the music services service are
			// zone players
			ZonePlayerDevice zonePlayerDevice = new ZonePlayerDevice();
			zonePlayerDevice.init(device, this.controlPoint);
			return zonePlayerDevice;
		}
		return null;
	}

	public MediaRendererDevice createMediaRendererDevice(ZonePlayerDevice player) {
		RemoteDevice parentDevice = player.getUpnpDevice();
		RemoteDevice childDevice = findChildDevice(parentDevice, MEDIA_RENDERER_DEVICE_ID);
		if (childDevice != null) {
			MediaRendererDevice mediaRendererDevice = new MediaRendererDevice();
			mediaRendererDevice.init(childDevice, this.controlPoint);
			return mediaRendererDevice;
		}
		return null;
	}

	protected RemoteDevice findChildDevice(RemoteDevice parentDevice, String childDeviceType) {
		RemoteDevice[] embeddedDevices = parentDevice.getEmbeddedDevices();
		UDADeviceType udaDeviceType = new UDADeviceType(childDeviceType);
		for (RemoteDevice embeddedDevice : embeddedDevices) {
			DeviceType type = embeddedDevice.getType();
			if (type.equals(udaDeviceType)) {
				return embeddedDevice;
			}
		}
		return null;
	}

}
