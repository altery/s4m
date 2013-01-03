package ch.ownz.s4m.sonos.actionexecution;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.teleal.cling.controlpoint.ControlPoint;
import org.teleal.cling.model.action.ActionInvocation;
import org.teleal.cling.model.meta.RemoteService;

/**
 * Executes actions on services sequentially.
 * 
 * @author altery
 * 
 */
public class SequentialServiceActionExecutor {

	private static final Logger LOG = LoggerFactory.getLogger(SequentialServiceActionExecutor.class);

	private final BlockingQueue<ActionExecutionResultFuture> queue = new LinkedBlockingDeque<ActionExecutionResultFuture>();

	private final ControlPoint controlPoint;

	private final Consumer consumer;

	public SequentialServiceActionExecutor(ControlPoint controlPoint) {
		this.controlPoint = controlPoint;
		this.consumer = new Consumer(controlPoint);
		new Thread(this.consumer).start();
	}

	public Future<Response> execute(Message message) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Queueing action invocation for execution: "
					+ message.getActionInvocation().getAction().getName());
		}
		ActionExecutionResultFuture future = new ActionExecutionResultFuture(message);
		this.queue.offer(future);
		return future;
	}

	public ControlPoint getControlPoint() {
		return this.controlPoint;
	}

	public void stop() {
		this.consumer.stop();
	}

	private class Consumer implements Runnable {

		private final ControlPoint controlPoint;

		private boolean running = true;

		public Consumer(ControlPoint controlPoint) {
			this.controlPoint = controlPoint;
		}

		public void stop() {
			this.running = false;
		}

		@Override
		public void run() {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Started ActionInvocation consumer");
			}
			while (this.running) {
				try {
					ActionExecutionResultFuture future = SequentialServiceActionExecutor.this.queue.poll(1,
							TimeUnit.SECONDS);
					if (future != null) {
						ActionInvocation<RemoteService> actionInvocation = future.getActionInvocation();
						if (LOG.isDebugEnabled()) {
							LOG.debug("Consuming action: " + actionInvocation.getAction().getName());
						}
						BlockingActionCallBack actionCallback = new BlockingActionCallBack(actionInvocation);
						this.controlPoint.execute(actionCallback);
						actionCallback.blockUntilExecuted();
						future.markAsExecuted();
						if (LOG.isDebugEnabled()) {
							LOG.debug("Current action invocation queue length: "
									+ SequentialServiceActionExecutor.this.queue.size());
						}
					}
				} catch (InterruptedException e) {
					LOG.warn("Interrupted while waiting for new ActionInvocations", e);
				}

			}
			SequentialServiceActionExecutor.this.queue.clear();
			if (LOG.isDebugEnabled()) {
				LOG.debug("Stopped ActionInvocation consumer");
			}
		}
	};
}
