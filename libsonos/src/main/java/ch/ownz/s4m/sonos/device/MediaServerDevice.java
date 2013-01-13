package ch.ownz.s4m.sonos.device;

import java.util.ArrayList;
import java.util.List;

import ch.ownz.s4m.sonos.service.ConnectionManagerService;
import ch.ownz.s4m.sonos.service.ContentDirectoryService;
import ch.ownz.s4m.sonos.service.SonosService;
import ch.ownz.s4m.sonos.service.SonosServiceFactory;

public class MediaServerDevice extends EmbeddedDevice {

	private ConnectionManagerService connectionManagerService;

	private ContentDirectoryService contentDirectoryService;

	@Override
	public void deviceUpdated() {
	}

	@Override
	protected List<SonosService> findServices() {
		SonosServiceFactory serviceFactory = new SonosServiceFactory();
		List<SonosService> services = new ArrayList<SonosService>();

		this.connectionManagerService = serviceFactory.createService(ConnectionManagerService.class, this);
		services.add(this.connectionManagerService);

		this.contentDirectoryService = serviceFactory.createService(ContentDirectoryService.class, this);
		services.add(this.contentDirectoryService);

		return services;
	}

}
