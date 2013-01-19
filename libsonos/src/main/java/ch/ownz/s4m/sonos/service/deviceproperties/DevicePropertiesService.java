package ch.ownz.s4m.sonos.service.deviceproperties;

import org.teleal.cling.model.gena.GENASubscription;
import org.teleal.cling.model.meta.RemoteService;

import ch.ownz.s4m.sonos.model.ZoneAttributes;
import ch.ownz.s4m.sonos.model.ZoneInfo;
import ch.ownz.s4m.sonos.service.SonosRemoteServiceReference;
import ch.ownz.s4m.sonos.service.SonosService;

public class DevicePropertiesService extends SonosService {

	public ZoneAttributes getZoneAttributes() {
		return new GetZoneAttributesAction(this).execute().waitForResponse().getZoneAttributes();
	}

	public ZoneInfo getZoneInfo() {
		return new GetZoneInfoAction(this).execute().waitForResponse().getZoneInfo();
	}

	@Override
	protected void handleServiceEvent(GENASubscription<RemoteService> subscription) {
	}

	@Override
	protected SonosRemoteServiceReference getRemoteServiceReference() {
		return SonosRemoteServiceReference.getDevicePropertiesServiceReference();
	}
}
