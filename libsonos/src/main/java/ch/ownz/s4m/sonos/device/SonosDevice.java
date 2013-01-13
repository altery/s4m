package ch.ownz.s4m.sonos.device;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.teleal.cling.controlpoint.ControlPoint;
import org.teleal.cling.model.meta.RemoteDevice;

import ch.ownz.s4m.sonos.actionexecution.Message;
import ch.ownz.s4m.sonos.actionexecution.Response;
import ch.ownz.s4m.sonos.actionexecution.SequentialServiceActionExecutor;
import ch.ownz.s4m.sonos.service.SonosService;

/**
 * Represents a UPNP Sonos device.
 * 
 * @author altery
 */
public abstract class SonosDevice {

	private static final Logger LOG = LoggerFactory.getLogger(SonosDevice.class);

	private boolean initialized;

	private RemoteDevice device;

	private ControlPoint controlPoint;

	private SequentialServiceActionExecutor serviceActionExecutor;

	private final List<SonosService> services = new ArrayList<SonosService>();

	private final List<EmbeddedDevice> embeddedDevices = new ArrayList<EmbeddedDevice>();

	public void init(RemoteDevice device, ControlPoint controlPoint) {
		if (this.initialized) {
			throw new IllegalStateException("Device already initialized");
		}
		this.device = device;
		this.controlPoint = controlPoint;
		this.serviceActionExecutor = new SequentialServiceActionExecutor(controlPoint);
		this.services.addAll(findServices());
		this.embeddedDevices.addAll(findEmbeddedDevices());
	}

	public ControlPoint getControlPoint() {
		return this.controlPoint;
	}

	public RemoteDevice getUpnpDevice() {
		return this.device;
	}

	public Future<Response> execute(Message message) {
		return this.serviceActionExecutor.execute(message);
	}

	public void dispose() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Disposing SonosDevice: " + this.device.getDisplayString());
		}
		for (EmbeddedDevice embeddedDevice : this.embeddedDevices) {
			embeddedDevice.dispose();
		}
		this.serviceActionExecutor.stop();
	}

	public abstract void deviceUpdated();

	protected abstract List<SonosService> findServices();

	protected abstract List<EmbeddedDevice> findEmbeddedDevices();

}
