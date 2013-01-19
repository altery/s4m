package ch.ownz.s4m.sonos.service.avtransport;

import org.teleal.cling.model.action.ActionInvocation;
import org.teleal.cling.model.meta.RemoteService;
import org.teleal.cling.model.types.UnsignedIntegerFourBytes;

import ch.ownz.s4m.action.NoResponseServiceAction;
import ch.ownz.s4m.sonos.service.SonosService;

public class PlayAction extends NoResponseServiceAction {

	private int instanceId = 0;
	private String speed = "1";

	public PlayAction(SonosService service) {
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
		return "Play";
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	@Override
	protected void fillInput(ActionInvocation<RemoteService> actionInvocation) {
		actionInvocation.setInput("InstanceID", new UnsignedIntegerFourBytes(this.instanceId));
		actionInvocation.setInput("Speed", this.speed);
	}

}
