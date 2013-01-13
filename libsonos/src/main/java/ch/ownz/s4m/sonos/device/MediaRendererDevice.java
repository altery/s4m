package ch.ownz.s4m.sonos.device;

import java.util.ArrayList;
import java.util.List;

import ch.ownz.s4m.sonos.service.AVTransportService;
import ch.ownz.s4m.sonos.service.ConnectionManagerService;
import ch.ownz.s4m.sonos.service.RenderingControlService;
import ch.ownz.s4m.sonos.service.SonosService;
import ch.ownz.s4m.sonos.service.SonosServiceFactory;

public class MediaRendererDevice extends EmbeddedDevice {

	private RenderingControlService renderingControlService;
	private AVTransportService avTransportService;
	private ConnectionManagerService connectionManagerService;

	public AVTransportService getAvTransportService() {
		return this.avTransportService;
	}

	public RenderingControlService getRenderingControlService() {
		return this.renderingControlService;
	}

	@Override
	protected List<SonosService> findServices() {
		SonosServiceFactory serviceFactory = new SonosServiceFactory();
		List<SonosService> services = new ArrayList<SonosService>();

		this.renderingControlService = serviceFactory.createService(RenderingControlService.class, this);
		services.add(this.renderingControlService);

		this.avTransportService = serviceFactory.createService(AVTransportService.class, this);
		services.add(this.avTransportService);

		this.connectionManagerService = serviceFactory.createService(ConnectionManagerService.class, this);
		services.add(this.connectionManagerService);

		return services;
	}

	@Override
	public void deviceUpdated() {
	}

}
