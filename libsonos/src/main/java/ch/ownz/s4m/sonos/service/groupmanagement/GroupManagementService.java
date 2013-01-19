package ch.ownz.s4m.sonos.service.groupmanagement;

import java.util.Map;

import org.teleal.cling.model.gena.GENASubscription;
import org.teleal.cling.model.meta.RemoteService;
import org.teleal.cling.model.state.StateVariableValue;

import ch.ownz.s4m.sonos.device.ZonePlayerDevice;
import ch.ownz.s4m.sonos.service.SonosRemoteServiceReference;
import ch.ownz.s4m.sonos.service.SonosService;

public class GroupManagementService extends SonosService {

	public void addMember(ZonePlayerDevice zonePlayer) {

	}

	public void removeMember(ZonePlayerDevice zonePlayer) {

	}

	@Override
	protected void handleServiceEvent(GENASubscription<RemoteService> subscription) {
		System.out.println("Got event from device " + getOwningDevice());

		System.out.println("Event: " + subscription.getCurrentSequence().getValue());

		Map<String, StateVariableValue<RemoteService>> values = subscription.getCurrentValues();
		System.out.println(values);
	}

	@Override
	protected SonosRemoteServiceReference getRemoteServiceReference() {
		return SonosRemoteServiceReference.getGroupManagementServiceReference();
	}

}
