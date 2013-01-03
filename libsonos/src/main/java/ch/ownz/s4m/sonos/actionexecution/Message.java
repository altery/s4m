package ch.ownz.s4m.sonos.actionexecution;

import org.teleal.cling.model.action.ActionInvocation;
import org.teleal.cling.model.meta.Action;
import org.teleal.cling.model.meta.RemoteService;

public class Message {

	private final ActionInvocation<RemoteService> actionInvocation;

	public Message(RemoteService service, String actionName) {
		Action<RemoteService> action = service.getAction(actionName);
		this.actionInvocation = new ActionInvocation<RemoteService>(action);
	}

	public void set(String argument, Object value) {
		this.actionInvocation.setInput(argument, value);
	}

	ActionInvocation<RemoteService> getActionInvocation() {
		return this.actionInvocation;
	}

}
