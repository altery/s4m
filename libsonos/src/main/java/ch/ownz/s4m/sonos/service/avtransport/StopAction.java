package ch.ownz.s4m.sonos.service.avtransport;

import org.teleal.cling.model.action.ActionInvocation;
import org.teleal.cling.model.meta.RemoteService;
import org.teleal.cling.model.types.UnsignedIntegerFourBytes;

import ch.ownz.s4m.action.NoResponseServiceAction;
import ch.ownz.s4m.sonos.service.SonosService;

public class StopAction extends NoResponseServiceAction {

	private int instanceId = 0;

	public StopAction(SonosService service) {
		super(service);
	}

	public void setInstanceId(int id) {
		if (id < 0) {
			throw new IllegalArgumentException("Id must not be negative");
		}
		this.instanceId = id;
	}

	@Override
	protected String getActionName() {
		return "Stop";
	}

	@Override
	protected void fillInput(ActionInvocation<RemoteService> actionInvocation) {
		actionInvocation.setInput("InstanceID", new UnsignedIntegerFourBytes(this.instanceId));
	}

}
