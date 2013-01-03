package ch.ownz.s4m.sonos.actionexecution;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.teleal.cling.model.action.ActionInvocation;
import org.teleal.cling.model.meta.RemoteService;

class ActionExecutionResultFuture implements Future<Response> {

	private final ActionInvocation<RemoteService> actionInvocation;

	private boolean done;

	public ActionExecutionResultFuture(Message message) {
		this.actionInvocation = message.getActionInvocation();
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		return false;
	}

	@Override
	public Response get() throws InterruptedException, ExecutionException {
		synchronized (this) {
			while (!this.done) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					// NOP
				}
			}
		}
		if (this.actionInvocation.getFailure() != null) {
			throw new ExecutionException("Failed to execute action", this.actionInvocation.getFailure());
		}
		return new Response(this.actionInvocation);
	}

	@Override
	public Response get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		synchronized (this) {
			while (!this.done) {
				try {
					this.wait(unit.toMillis(timeout));
					if (!this.done) {
						throw new TimeoutException();
					}
				} catch (InterruptedException e) {
					// NOP
				}
			}
		}
		if (this.actionInvocation.getFailure() != null) {
			throw new ExecutionException("Failed to execute action", this.actionInvocation.getFailure());
		}
		return new Response(this.actionInvocation);
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public boolean isDone() {
		return this.done;
	}

	ActionInvocation<RemoteService> getActionInvocation() {
		return this.actionInvocation;
	}

	void markAsExecuted() {
		synchronized (this) {
			this.done = true;
			this.notifyAll();
		}
	}

}