package ch.ownz.s4m.sonos.service.deviceproperties;

import java.util.Map;

import org.teleal.cling.model.action.ActionArgumentValue;
import org.teleal.cling.model.action.ActionInvocation;
import org.teleal.cling.model.meta.RemoteService;

import ch.ownz.s4m.action.ServiceAction;
import ch.ownz.s4m.action.ServiceActionResponse;
import ch.ownz.s4m.sonos.model.ZoneInfo;
import ch.ownz.s4m.sonos.service.SonosService;
import ch.ownz.s4m.sonos.service.deviceproperties.GetZoneInfoAction.GetZoneInfoResponse;

public class GetZoneInfoAction extends ServiceAction<GetZoneInfoResponse> {

	public static class GetZoneInfoResponse extends ServiceActionResponse {

		private ZoneInfo zoneInfo;

		public ZoneInfo getZoneInfo() {
			return this.zoneInfo;
		};

		@Override
		protected void processOutput(Map<String, ActionArgumentValue<RemoteService>> responseMap) {
			String serialNumber = (String) responseMap.get("SerialNumber").getValue();
			String softwareVersion = (String) responseMap.get("SoftwareVersion").getValue();
			String displaySoftwareVersion = (String) responseMap.get("DisplaySoftwareVersion").getValue();
			String hardwareVersion = (String) responseMap.get("HardwareVersion").getValue();
			String ipAddress = (String) responseMap.get("IPAddress").getValue();
			String macAddress = (String) responseMap.get("MACAddress").getValue();
			String copyrightInfo = (String) responseMap.get("CopyrightInfo").getValue();
			String extraInfoString = (String) responseMap.get("ExtraInfo").getValue();

			this.zoneInfo = new ZoneInfo(serialNumber, softwareVersion, displaySoftwareVersion, hardwareVersion,
					ipAddress, macAddress, copyrightInfo, extraInfoString);
		}

	}

	public GetZoneInfoAction(SonosService service) {
		super(service);
	}

	@Override
	protected String getActionName() {
		return "GetZoneInfo";
	}

	@Override
	protected GetZoneInfoResponse createActionResponse() {
		return new GetZoneInfoResponse();
	}

	@Override
	protected void fillInput(ActionInvocation<RemoteService> actionInvocation) {

	}
}
