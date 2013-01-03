package ch.ownz.s4m.sonos.device;

import java.util.ArrayList;
import java.util.List;

import ch.ownz.s4m.sonos.service.DevicePropertiesService;
import ch.ownz.s4m.sonos.service.RenderingControlService;
import ch.ownz.s4m.sonos.service.SonosService;
import ch.ownz.s4m.sonos.service.SonosServiceFactory;

public class ZonePlayerDevice extends SonosDevice {

	private RenderingControlService renderingControlService;

	private DevicePropertiesService devicePropertiesService;

	private MediaRendererDevice mediaRendererDevice;

	public DevicePropertiesService getDevicePropertiesService() {
		return this.devicePropertiesService;
	}

	public MediaRendererDevice getMediaRendererDevice() {
		return this.mediaRendererDevice;
	}

	public RenderingControlService getRenderingControlService() {
		return this.renderingControlService;
	}

	@Override
	public void deviceUpdated() {
	}

	@Override
	protected List<SonosService> findServices() {
		SonosServiceFactory serviceFactory = new SonosServiceFactory();
		List<SonosService> services = new ArrayList<SonosService>();

		this.renderingControlService = serviceFactory.createService(RenderingControlService.class, this);
		services.add(this.renderingControlService);

		this.devicePropertiesService = serviceFactory.createService(DevicePropertiesService.class, this);
		services.add(this.devicePropertiesService);

		return services;
	}

	@Override
	protected List<SonosDevice> findChildDevices() {
		List<SonosDevice> devices = new ArrayList<SonosDevice>();
		DeviceFactory deviceFactory = new DeviceFactory(getControlPoint());

		this.mediaRendererDevice = deviceFactory.createMediaRendererDevice(this);
		devices.add(this.mediaRendererDevice);

		return devices;
	}

	@Override
	public String toString() {
		return this.devicePropertiesService.getZoneAttributes().getName();
	}

}
