package ch.ownz.s4m.sonos.service.deviceproperties;

import java.util.Map;

import org.teleal.cling.model.action.ActionArgumentValue;
import org.teleal.cling.model.action.ActionInvocation;
import org.teleal.cling.model.meta.RemoteService;

import ch.ownz.s4m.action.ServiceAction;
import ch.ownz.s4m.action.ServiceActionResponse;
import ch.ownz.s4m.sonos.model.ZoneAttributes;
import ch.ownz.s4m.sonos.service.SonosService;
import ch.ownz.s4m.sonos.service.deviceproperties.GetZoneAttributesAction.GetZoneAttributesActionResponse;

public class GetZoneAttributesAction extends ServiceAction<GetZoneAttributesActionResponse> {

	public static class GetZoneAttributesActionResponse extends ServiceActionResponse {

		private ZoneAttributes zoneAttributes;

		public ZoneAttributes getZoneAttributes() {
			return this.zoneAttributes;
		};

		@Override
		protected void processOutput(Map<String, ActionArgumentValue<RemoteService>> responseMap) {
			String zoneName = (String) responseMap.get("CurrentZoneName").getValue();
			this.zoneAttributes = new ZoneAttributes(zoneName);
		}

	}

	public GetZoneAttributesAction(SonosService service) {
		super(service);
	}

	@Override
	protected String getActionName() {
		return "GetZoneAttributes";
	}

	@Override
	protected GetZoneAttributesActionResponse createActionResponse() {
		return new GetZoneAttributesActionResponse();
	}

	@Override
	protected void fillInput(ActionInvocation<RemoteService> actionInvocation) {

	}
}
