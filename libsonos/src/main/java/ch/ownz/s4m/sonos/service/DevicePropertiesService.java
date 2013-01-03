package ch.ownz.s4m.sonos.service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.teleal.cling.model.gena.GENASubscription;
import org.teleal.cling.model.meta.RemoteService;

import ch.ownz.s4m.sonos.actionexecution.Message;
import ch.ownz.s4m.sonos.actionexecution.Response;
import ch.ownz.s4m.sonos.model.ZoneAttributes;
import ch.ownz.s4m.sonos.model.ZoneInfo;

public class DevicePropertiesService extends SonosService {

	private static final Logger LOG = LoggerFactory.getLogger(DevicePropertiesService.class);

	public ZoneAttributes getZoneAttributes() {
		Message message = createMessage("GetZoneAttributes");
		Future<Response> future = getOwningDevice().execute(message);
		try {
			Response response = future.get();
			String zoneName = (String) response.get("CurrentZoneName").getValue();
			ZoneAttributes zoneAttributes = new ZoneAttributes(zoneName);
			return zoneAttributes;
		} catch (InterruptedException e) {
			LOG.error("Failed to get zone attributes", e);
		} catch (ExecutionException e) {
			LOG.error("Failed to get zone attributes", e);
		}
		return null;
	}

	public ZoneInfo getZoneInfo() {
		Message message = createMessage("GetZoneInfo");
		Future<Response> future = getOwningDevice().execute(message);
		try {
			Response response = future.get();
			String serialNumber = (String) response.get("SerialNumber").getValue();
			String softwareVersion = (String) response.get("SoftwareVersion").getValue();
			String displaySoftwareVersion = (String) response.get("DisplaySoftwareVersion").getValue();
			String hardwareVersion = (String) response.get("HardwareVersion").getValue();
			String ipAddress = (String) response.get("IPAddress").getValue();
			String macAddress = (String) response.get("MACAddress").getValue();
			String copyrightInfo = (String) response.get("CopyrightInfo").getValue();
			String extraInfoString = (String) response.get("ExtraInfo").getValue();

			ZoneInfo zoneInfo = new ZoneInfo(serialNumber, softwareVersion, displaySoftwareVersion, hardwareVersion,
					ipAddress, macAddress, copyrightInfo, extraInfoString);
			return zoneInfo;
		} catch (InterruptedException e) {
			LOG.error("Failed to get zone info", e);
		} catch (ExecutionException e) {
			LOG.error("Failed to get zone info", e);
		}
		return null;
	}

	@Override
	protected void handleServiceEvent(GENASubscription<RemoteService> subscription) {
	}

	@Override
	protected SonosRemoteServiceReference getRemoteServiceReference() {
		return SonosRemoteServiceReference.getDevicePropertiesServiceReference();
	}
}
