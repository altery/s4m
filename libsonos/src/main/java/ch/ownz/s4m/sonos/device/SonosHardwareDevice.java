package ch.ownz.s4m.sonos.device;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.ownz.s4m.sonos.service.SonosService;
import ch.ownz.s4m.sonos.service.SonosServiceFactory;
import ch.ownz.s4m.sonos.service.deviceproperties.DevicePropertiesService;
import ch.ownz.s4m.sonos.service.groupmanagement.GroupManagementService;
import ch.ownz.s4m.sonos.service.systemproperties.SystemPropertiesService;
import ch.ownz.s4m.sonos.service.zonegrouptopology.ZoneGroupTopologyService;

/**
 * Represents an actual Sonos device, that provides common functionality.
 * 
 * <p>
 * All {@link SonosDevice} that represent real hardware (PLAY:5, BRIDGE, ...)
 * are subclasses of this class.
 * </p>
 * 
 * <p>
 * <b>TODO: Does the SUB fit in (I don't own one)?</b>
 * </p>
 * 
 * @author altery
 * 
 */
public class SonosHardwareDevice extends SonosDevice {

	private DevicePropertiesService devicePropertiesService;

	private GroupManagementService groupManagementService;

	private ZoneGroupTopologyService zoneGroupTopologyService;

	private SystemPropertiesService systemPropertiesService;

	public DevicePropertiesService getDevicePropertiesService() {
		return this.devicePropertiesService;
	}

	@Override
	public void deviceUpdated() {
	}

	@Override
	protected List<SonosService> findServices() {
		SonosServiceFactory serviceFactory = new SonosServiceFactory();
		List<SonosService> services = new ArrayList<SonosService>();

		this.devicePropertiesService = serviceFactory.createService(DevicePropertiesService.class, this);
		services.add(this.devicePropertiesService);

		this.groupManagementService = serviceFactory.createService(GroupManagementService.class, this);
		services.add(this.groupManagementService);

		this.zoneGroupTopologyService = serviceFactory.createService(ZoneGroupTopologyService.class, this);
		services.add(this.zoneGroupTopologyService);

		this.systemPropertiesService = serviceFactory.createService(SystemPropertiesService.class, this);
		services.add(this.systemPropertiesService);

		return services;
	}

	@Override
	protected List<EmbeddedDevice> findEmbeddedDevices() {
		return Collections.emptyList();
	}

	@Override
	public String toString() {
		return this.devicePropertiesService.getZoneAttributes().getName();
	}
}
