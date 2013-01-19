package ch.ownz.s4m.action;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Represents the future response of an action invocation.
 * 
 * @author altery
 * 
 * @param <T>
 *            response type
 */
public class FutureActionResponse<T> {

	private final Future<T> future;

	public FutureActionResponse(Future<T> future) {
		this.future = future;
	}

	/**
	 * Wait for the response, blocking if necessary.
	 * 
	 * @return the response
	 */
	public T waitForResponse() {
		try {
			return this.future.get();
		} catch (InterruptedException e) {
			throw new IllegalStateException("Got interrupted while waiting for the response", e);
		} catch (ExecutionException e) {
			throw new IllegalStateException("Unexpected exception while executing the action", e);
		}
	}
}
