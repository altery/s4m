package ch.ownz.s4m.action;

import java.util.Map;

import org.teleal.cling.model.action.ActionArgumentValue;
import org.teleal.cling.model.message.UpnpResponse;
import org.teleal.cling.model.meta.RemoteService;

/**
 * Base implementation for classes that represent responses of actions on
 * services.
 * 
 * <p>
 * This call records action invocation failures and also provides a default
 * implementation for action that do have responses.
 * </p>
 * 
 * @author altery
 * 
 */
public abstract class ServiceActionResponse {

	private UpnpResponse response;
	private String errorMessage;
	private boolean failed;

	public boolean isFailed() {
		return this.failed;
	}

	/**
	 * @return a human readable error message, in case the action invocation
	 *         failed.
	 */
	public String getErrorMessage() {
		return this.errorMessage;
	}

	/**
	 * Returns the low-level UPNP response.
	 * 
	 * <p>
	 * Only available in case the action invocation failed.
	 * </p>
	 * 
	 * @return the upnp response, may be <code>null</code>
	 */
	public UpnpResponse getReponse() {
		return this.response;
	}

	/**
	 * Tracks that the action invocation has failed.
	 * 
	 * @param operation
	 *            the upnp operation (response)
	 * @param defaultMsg
	 *            the default, human readable message
	 */
	protected void failed(UpnpResponse operation, String defaultMsg) {
		this.response = operation;
		this.errorMessage = defaultMsg;
		this.failed = true;
	}

	/**
	 * Processes the output of an action invocation.
	 * 
	 * <p>
	 * Clients of this class must implement this method and provide access to
	 * the processed output by extending this class with additional methods.
	 * </p>
	 * 
	 * @param responseMap
	 *            a map from arguments to argument values
	 */
	protected abstract void processOutput(Map<String, ActionArgumentValue<RemoteService>> responseMap);

	/**
	 * An implementation that can be used for action with no response.
	 * 
	 * @author altery
	 * 
	 */
	public static class Void extends ServiceActionResponse {

		@Override
		protected void processOutput(Map<String, ActionArgumentValue<RemoteService>> responseMap) {
			// No response handling necessary
		}

	}
}
