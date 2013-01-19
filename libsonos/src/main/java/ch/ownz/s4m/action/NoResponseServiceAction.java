package ch.ownz.s4m.action;

import ch.ownz.s4m.action.ServiceActionResponse.Void;
import ch.ownz.s4m.sonos.service.SonosService;

/**
 * Represents an action that does not return a response.
 * 
 * <p>
 * Class
 * 
 * @author altery
 */
public abstract class NoResponseServiceAction extends ServiceAction<Void> {

	public NoResponseServiceAction(SonosService service) {
		super(service);
	}

	@Override
	protected Void createActionResponse() {
		return new ServiceActionResponse.Void();
	}
}
