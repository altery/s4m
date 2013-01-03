package ch.ownz.s4m.sonos.model;

public class ZoneInfo {

	private final String serialNumber;
	private final String softwareVersion;
	private final String displaySoftwareVersion;
	private final String hardwareVersion;
	private final String ipAddress;
	private final String macAddress;
	private final String copyrightInfo;
	private final String extraInfoString;

	public ZoneInfo(String serialNumber, String softwareVersion, String displaySoftwareVersion, String hardwareVersion,
			String ipAddress, String macAddress, String copyrightInfo, String extraInfoString) {
		this.serialNumber = serialNumber;
		this.softwareVersion = softwareVersion;
		this.displaySoftwareVersion = displaySoftwareVersion;
		this.hardwareVersion = hardwareVersion;
		this.ipAddress = ipAddress;
		this.macAddress = macAddress;
		this.copyrightInfo = copyrightInfo;
		this.extraInfoString = extraInfoString;
	}

	public String getSerialNumber() {
		return this.serialNumber;
	}

	public String getSoftwareVersion() {
		return this.softwareVersion;
	}

	public String getDisplaySoftwareVersion() {
		return this.displaySoftwareVersion;
	}

	public String getHardwareVersion() {
		return this.hardwareVersion;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public String getMacAddress() {
		return this.macAddress;
	}

	public String getCopyrightInfo() {
		return this.copyrightInfo;
	}

	public String getExtraInfoString() {
		return this.extraInfoString;
	}

}
