package ch.ownz.s4m.sonos.service.renderingcontrol;

import java.util.Map;

import org.teleal.cling.model.action.ActionArgumentValue;
import org.teleal.cling.model.action.ActionInvocation;
import org.teleal.cling.model.meta.RemoteService;
import org.teleal.cling.model.types.UnsignedIntegerTwoBytes;

import ch.ownz.s4m.action.ServiceAction;
import ch.ownz.s4m.action.ServiceActionResponse;
import ch.ownz.s4m.sonos.service.SonosService;
import ch.ownz.s4m.sonos.service.renderingcontrol.GetVolumeAction.GetVolumeActionResponse;

public class GetVolumeAction extends ServiceAction<GetVolumeActionResponse> {

	public GetVolumeAction(SonosService service) {
		super(service);
	}

	@Override
	protected String getActionName() {
		return "GetVolume";
	}

	@Override
	protected GetVolumeActionResponse createActionResponse() {
		return new GetVolumeActionResponse();
	}

	@Override
	protected void fillInput(ActionInvocation<RemoteService> actionInvocation) {
		actionInvocation.setInput("Channel", "Master");
	}

	public static class GetVolumeActionResponse extends ServiceActionResponse {

		private int value;

		@Override
		protected void processOutput(Map<String, ActionArgumentValue<RemoteService>> responseMap) {
			this.value = ((UnsignedIntegerTwoBytes) responseMap.get("CurrentVolume").getValue()).getValue().intValue();
		}

		public int getValue() {
			return this.value;
		}

	}

}
