package ch.ownz.s4m.sonos.device;

import java.util.ArrayList;
import java.util.List;

import ch.ownz.s4m.sonos.service.SonosService;
import ch.ownz.s4m.sonos.service.SonosServiceFactory;
import ch.ownz.s4m.sonos.service.alarmclock.AlarmClockService;
import ch.ownz.s4m.sonos.service.audioin.AudioInService;
import ch.ownz.s4m.sonos.service.musicservices.MusicServicesService;
import ch.ownz.s4m.sonos.service.renderingcontrol.RenderingControlService;

/**
 * Represents a Sonos ZonePlayer.
 * 
 * <p>
 * ZonePlayers are PLAY:5 (S5), PLAY:3, CONNECT:AMP (ZP120), CONNECT (ZP90).
 * </p>
 * 
 * @author altery
 * 
 */
public class ZonePlayerDevice extends SonosHardwareDevice {

	private MediaRendererDevice mediaRendererDevice;

	private MediaServerDevice mediaServerDevice;

	private AlarmClockService alarmClockService;

	private AudioInService audioInService;

	private MusicServicesService musicServicesService;

	private RenderingControlService renderingControlService;

	@Override
	public MediaRendererDevice getMediaRendererDevice() {
		return this.mediaRendererDevice;
	}

	public AlarmClockService getAlarmClockService() {
		return this.alarmClockService;
	}

	public AudioInService getAudioInService() {
		return this.audioInService;
	}

	public MusicServicesService getMusicServicesService() {
		return this.musicServicesService;
	}

	public RenderingControlService getRenderingControlService() {
		return this.renderingControlService;
	}

	@Override
	protected List<SonosService> findServices() {
		SonosServiceFactory serviceFactory = new SonosServiceFactory();
		List<SonosService> services = super.findServices();

		this.musicServicesService = serviceFactory.createService(MusicServicesService.class, this);
		services.add(this.musicServicesService);

		this.renderingControlService = serviceFactory.createService(RenderingControlService.class, this);
		services.add(this.renderingControlService);

		this.alarmClockService = serviceFactory.createService(AlarmClockService.class, this);
		services.add(this.alarmClockService);

		this.audioInService = serviceFactory.createService(AudioInService.class, this);
		services.add(this.audioInService);

		return services;
	}

	@Override
	protected List<EmbeddedDevice> findEmbeddedDevices() {
		List<EmbeddedDevice> devices = new ArrayList<EmbeddedDevice>();
		DeviceFactory deviceFactory = new DeviceFactory(getControlPoint());

		this.mediaRendererDevice = deviceFactory.createMediaRendererDevice(this);
		devices.add(this.mediaRendererDevice);

		this.mediaServerDevice = deviceFactory.createMediaServerDevice(this);
		devices.add(this.mediaServerDevice);

		return devices;
	}

}
