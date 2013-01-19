package ch.ownz.s4m.sonos.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.teleal.cling.controlpoint.SubscriptionCallback;
import org.teleal.cling.model.action.ActionInvocation;
import org.teleal.cling.model.gena.CancelReason;
import org.teleal.cling.model.gena.GENASubscription;
import org.teleal.cling.model.message.UpnpResponse;
import org.teleal.cling.model.meta.RemoteService;

import ch.ownz.s4m.sonos.device.SonosDevice;

public abstract class SonosService {

	private static final Logger LOG = LoggerFactory.getLogger(SonosService.class);

	private RemoteService remoteService;

	private boolean initialized;

	private SonosDevice owningDevice;

	public void init(RemoteService remoteService, SonosDevice owningDevice) {
		if (this.initialized) {
			throw new IllegalStateException("Service already initialized");
		}
		this.remoteService = remoteService;
		this.owningDevice = owningDevice;
		registerServiceEventListener();
		this.initialized = true;
	}

	protected void logActionInvocationError(ActionInvocation<?> invocation, UpnpResponse operation, String defaultMsg) {
		LOG.error("Failed to invoke action " + invocation.toString() + ": " + defaultMsg);
	}

	private void registerServiceEventListener() {
		@SuppressWarnings({ "rawtypes", "unchecked" })
		SubscriptionCallback serviceEventSubscription = new SubscriptionCallback(this.remoteService) {

			@Override
			protected void failed(GENASubscription subscription, UpnpResponse responseStatus, Exception exception,
					String defaultMsg) {
				LOG.error("Service event subscription failed for service " + subscription.getService(), exception);
			}

			@Override
			protected void eventsMissed(GENASubscription subscription, int numberOfMissedEvents) {
				LOG.warn("Service events missed: " + numberOfMissedEvents);
			}

			@Override
			protected void eventReceived(GENASubscription subscription) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("Service event received:" + subscription);
				}
				handleServiceEvent(subscription);
			}

			@Override
			protected void established(GENASubscription subscription) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("Service event subscription established");
				}
			}

			@Override
			protected void ended(GENASubscription subscription, CancelReason reason, UpnpResponse responseStatus) {
				if (LOG.isDebugEnabled()) {
					String reasonText = "unknown";
					if (reason != null) {
						reasonText = reason.toString();
					}
					LOG.debug("Service event subscription has ended. Reason: " + reasonText);
				}
			}
		};
		this.owningDevice.getControlPoint().execute(serviceEventSubscription);

	}

	public RemoteService getRemoteService() {
		return this.remoteService;
	}

	public SonosDevice getOwningDevice() {
		return this.owningDevice;
	}

	protected abstract void handleServiceEvent(GENASubscription<RemoteService> subscription);

	protected abstract SonosRemoteServiceReference getRemoteServiceReference();

}
