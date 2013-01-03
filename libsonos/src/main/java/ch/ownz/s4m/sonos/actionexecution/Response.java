package ch.ownz.s4m.sonos.actionexecution;

import java.util.Map;

import org.teleal.cling.model.action.ActionArgumentValue;
import org.teleal.cling.model.action.ActionInvocation;
import org.teleal.cling.model.meta.RemoteService;

public class Response {

	private final ActionInvocation<RemoteService> actionInvocation;

	Response(ActionInvocation<RemoteService> actionInvocation) {
		this.actionInvocation = actionInvocation;
	}

	public ActionArgumentValue<RemoteService> get(String argument) {
		return this.actionInvocation.getOutput(argument);
	}

	public Map<String, ActionArgumentValue<RemoteService>> getMap() {
		return this.actionInvocation.getOutputMap();
	}

}
