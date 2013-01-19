package ch.ownz.s4m.sonos.service.renderingcontrol;

import java.util.Map;
import java.util.Map.Entry;

import org.teleal.cling.model.gena.GENASubscription;
import org.teleal.cling.model.meta.RemoteService;
import org.teleal.cling.model.state.StateVariableValue;

import ch.ownz.s4m.sonos.service.ServiceProperty;
import ch.ownz.s4m.sonos.service.SonosRemoteServiceReference;
import ch.ownz.s4m.sonos.service.SonosService;
import ch.ownz.s4m.sonos.xml.RenderingControlEventHandler;
import ch.ownz.s4m.sonos.xml.RenderingControlEventHandler.RenderingControlEventType;
import ch.ownz.s4m.sonos.xml.XMLParserUtil;

public class RenderingControlService extends SonosService {

	public enum Property implements ServiceProperty {
		VOLUME;
	}

	public void setMute(boolean mute) {
		new SetMuteAction(this, mute).execute().waitForResponse();
	}

	public int getVolume() {
		return new GetVolumeAction(this).execute().waitForResponse().getValue();
	}

	public void setVolume(int volume) {
		new SetVolumeAction(this, volume).execute().waitForResponse();
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
		}
	}

	@Override
	protected SonosRemoteServiceReference getRemoteServiceReference() {
		return SonosRemoteServiceReference.getRenderingControlServiceReference();
	}

}
