package ch.ownz.s4m.sonos.service;

import java.util.Map;

import org.teleal.cling.model.gena.GENASubscription;
import org.teleal.cling.model.meta.RemoteService;
import org.teleal.cling.model.state.StateVariableValue;

import ch.ownz.s4m.sonos.actionexecution.Message;
import ch.ownz.s4m.sonos.device.ZonePlayerDevice;

public class GroupManagementService extends SonosService {

	public void addMember(ZonePlayerDevice zonePlayer) {
		Message message = createMessage("AddMember");
		message.set("MemberID", "");
	}

	public void removeMember(ZonePlayerDevice zonePlayer) {
		Message message = createMessage("RemoveMember");
		message.set("MemberID", "");
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
