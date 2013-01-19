package ch.ownz.s4m.action;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.teleal.cling.controlpoint.ActionCallback;
import org.teleal.cling.controlpoint.ControlPoint;
import org.teleal.cling.model.action.ActionInvocation;
import org.teleal.cling.model.message.UpnpResponse;
import org.teleal.cling.model.meta.Action;
import org.teleal.cling.model.meta.RemoteService;

import ch.ownz.s4m.sonos.service.SonosService;

/**
 * Base implementation for classes that represent executable actions on
 * services.
 * 
 * @author altery
 * 
 * @param <T>
 *            the type of the response, may be
 *            {@link ServiceActionResponse.Void} for actions that do not result
 *            in a response.
 */
public abstract class ServiceAction<T extends ServiceActionResponse> {

	private static final Logger LOG = LoggerFactory.getLogger(ServiceAction.class);

	private final Action<RemoteService> action;
	private final ControlPoint controlPoint;
	private final DefaultCallable callable;
	private ExecutorService executorService;

	/**
	 * Creates a new action.
	 * 
	 * @param service
	 *            the service this action is supposed to be executed on
	 */
	public ServiceAction(SonosService service) {
		this.controlPoint = service.getOwningDevice().getControlPoint();
		this.action = service.getRemoteService().getAction(getActionName());
		if (this.action == null) {
			throw new IllegalArgumentException("Unknown action: " + getActionName());
		}
		this.callable = new DefaultCallable();
	}

	/**
	 * Sets an explicit executor service that manages execution of this
	 * callable. If no executor is set, the executor service of the control
	 * point is used, if available.
	 * 
	 * @param executorService
	 */
	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}

	/**
	 * The action name is a value that is specific to the service.
	 * 
	 * @return the name of the action.
	 */
	protected abstract String getActionName();

	/**
	 * Delegates the creation of an action response object to the client of this
	 * class.
	 * 
	 * @return a new instance of a subclass of {@link ServiceActionResponse}.
	 *         Client are not required to perform any additional initialization.
	 */
	protected abstract T createActionResponse();

	/**
	 * Fills the given {@link ActionInvocation} instance with inputs values.
	 * Clients may use constant values or extend this class with methods to
	 * provide those values.
	 * 
	 * @param actionInvocation
	 *            to be filled by calling
	 *            {@link ActionInvocation#setInput(String, Object)}
	 */
	protected abstract void fillInput(ActionInvocation<RemoteService> actionInvocation);

	/**
	 * Executes the action.
	 * 
	 * <p>
	 * Depending on the {@link ExecutorService} being used, the action is
	 * usually executed asynchronously and this method immediately returns. To
	 * wait for the response, call
	 * {@link FutureActionResponse#waitForResponse()} on the object returned by
	 * this method.
	 * </p>
	 * 
	 * @return an object representing the future response.
	 */
	public FutureActionResponse<T> execute() {
		if (this.executorService != null) {
			return new FutureActionResponse<T>(this.executorService.submit(this.callable));
		}
		Executor syncProtocolExecutor = this.controlPoint.getConfiguration().getSyncProtocolExecutor();
		if (syncProtocolExecutor instanceof ExecutorService) {
			return new FutureActionResponse<T>(((ExecutorService) syncProtocolExecutor).submit(this.callable));
		} else {
			throw new IllegalStateException(
					"No executor service available on the UPNP stack. An explicit executor service must be set");
		}
	}

	ControlPoint getControlPoint() {
		return this.controlPoint;
	}

	public Action<RemoteService> getAction() {
		return this.action;
	}

	private class DefaultCallable implements Callable<T> {

		@Override
		public T call() throws Exception {
			ActionInvocation<RemoteService> actionInvocation = new ActionInvocation<RemoteService>(getAction());
			fillInput(actionInvocation);
			DefaultCallback callback = new DefaultCallback(actionInvocation, getControlPoint());
			getControlPoint().execute(callback);
			return callback.getActionResponse();
		}

	}

	@SuppressWarnings("rawtypes")
	private class DefaultCallback extends ActionCallback {

		private T actionResponse;

		private boolean responseReceived;

		protected DefaultCallback(ActionInvocation actionInvocation, ControlPoint controlPoint) {
			super(actionInvocation, controlPoint);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void success(ActionInvocation invocation) {
			Map outputMap = invocation.getOutputMap();
			this.actionResponse = createActionResponse();
			this.actionResponse.processOutput(outputMap);
			synchronized (this) {
				this.responseReceived = true;
				notifyAll();
			}
			if (LOG.isDebugEnabled()) {
				LOG.debug("Action invocation succeeded: " + invocation.getAction().getName());
			}
		}

		public T getActionResponse() {
			synchronized (this) {
				while (!this.responseReceived) {
					try {
						wait();
					} catch (InterruptedException e) {
						// fall-through
					}
				}
			}
			return this.actionResponse;
		}

		@Override
		public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
			this.actionResponse = createActionResponse();
			this.actionResponse.failed(operation, defaultMsg);
			synchronized (this) {
				this.responseReceived = true;
				notifyAll();
			}
			if (LOG.isDebugEnabled()) {
				LOG.debug("Action invocation failed: " + invocation.getAction().getName() + ". Message: " + defaultMsg);
			}
		}

	}

}
