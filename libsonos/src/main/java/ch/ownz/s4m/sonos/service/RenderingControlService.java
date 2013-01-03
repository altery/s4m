package ch.ownz.s4m.sonos.service;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.teleal.cling.model.gena.GENASubscription;
import org.teleal.cling.model.meta.RemoteService;
import org.teleal.cling.model.state.StateVariableValue;
import org.teleal.cling.model.types.UnsignedIntegerTwoBytes;

import ch.ownz.s4m.sonos.actionexecution.Message;
import ch.ownz.s4m.sonos.actionexecution.Response;
import ch.ownz.s4m.sonos.xml.RenderingControlEventHandler;
import ch.ownz.s4m.sonos.xml.RenderingControlEventHandler.RenderingControlEventType;
import ch.ownz.s4m.sonos.xml.XMLParserUtil;

public class RenderingControlService extends SonosService {

	private static final Logger LOG = LoggerFactory.getLogger(RenderingControlService.class);

	private static final String SET_VOLUME_ACTION = "SetVolume";
	private static final String GET_VOLUME_ACTION = "GetVolume";
	private static final String SET_MUTE_ACTION = "SetMute";

	public enum Property implements ServiceProperty {
		VOLUME;
	}

	public void setMute(boolean mute) {
		Message message = createMessage(SET_MUTE_ACTION);
		message.set("InstanceID", "0");
		message.set("Channel", "Master"); // can also be LF or RF
		message.set("DesiredMute", mute ? "1" : "0");
		getOwningDevice().execute(message);
	}

	public int getVolume() {
		Integer volume = getState(Property.VOLUME, Integer.class);
		if (volume == null || true) {
			Message message = createMessage(GET_VOLUME_ACTION);
			message.set("Channel", "Master"); // can also be LF or
												// RF
			Future<Response> future = getOwningDevice().execute(message);
			try {
				Response response = future.get();
				UnsignedIntegerTwoBytes value = (UnsignedIntegerTwoBytes) response.get("CurrentVolume").getValue();
				volume = value.getValue().intValue();
				saveState(Property.VOLUME, volume);
			} catch (InterruptedException e) {
				LOG.error("Failed to get volume", e);
			} catch (ExecutionException e) {
				LOG.error("Failed to get volume", e);
			}

		}
		return volume;
	}

	public void setVolume(int volume) {
		Message message = createMessage(SET_VOLUME_ACTION);
		message.set("InstanceID", "0");
		message.set("Channel", "Master"); // can also be LF or RF
		message.set("DesiredVolume", String.valueOf(volume));
		getOwningDevice().execute(message);
	}

	@Override
	protected void handleServiceEvent(GENASubscription<RemoteService> subscription) {
		Map<String, StateVariableValue<RemoteService>> currentValues = subscription.getCurrentValues();
		for (Entry<String, StateVariableValue<RemoteService>> entry : currentValues.entrySet()) {
			StateVariableValue<RemoteService> stateVariableValue = entry.getValue();
			String xml = (String) stateVariableValue.getValue();
			RenderingControlEventHandler handler = new RenderingControlEventHandler();
			XMLParserUtil.parse(xml, handler);
			Map<RenderingControlEventType, String> changes = handler.getChanges();
			applyChanges(changes);
		}
	}

	private void applyChanges(Map<RenderingControlEventType, String> changes) {
		for (Entry<RenderingControlEventType, String> entry : changes.entrySet()) {
			RenderingControlEventType eventType = entry.getKey();
			String value = entry.getValue();
			switch (eventType) {
			case VOLUME_MASTER:
				int volume = Integer.parseInt(value);
				saveState(Property.VOLUME, volume);
				break;

			default:
				break;
			}
		}
	}

	@Override
	protected SonosRemoteServiceReference getRemoteServiceReference() {
		return SonosRemoteServiceReference.getRenderingControlServiceReference();
	}

}
