package ch.ownz.s4m.sonos.actionexecution;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.teleal.cling.controlpoint.ActionCallback;
import org.teleal.cling.model.action.ActionInvocation;
import org.teleal.cling.model.message.UpnpResponse;

@SuppressWarnings("rawtypes")
class BlockingActionCallBack extends ActionCallback {

	private static final Logger LOG = LoggerFactory.getLogger(BlockingActionCallBack.class);

	private volatile boolean executed;

	protected BlockingActionCallBack(ActionInvocation actionInvocation) {
		super(actionInvocation);
	}

	public void blockUntilExecuted() {
		synchronized (this) {
			while (!this.executed) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					// NOP
				}
			}
		}
	}

	public boolean hasFailed() {
		return getActionInvocation().getFailure() != null;
	}

	@Override
	public void success(ActionInvocation invocation) {
		synchronized (this) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Action invocation succeeded: " + invocation.getAction().getName());
			}
			this.executed = true;
			this.notifyAll();
		}
	}

	@Override
	public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
		synchronized (this) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Action invocation failed: " + invocation.getAction().getName() + ", " + defaultMsg);
			}
			this.executed = true;
			this.notifyAll();
		}
	}
}