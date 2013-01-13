package ch.ownz.s4m;

import org.jnativehook.GlobalScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.teleal.cling.UpnpServiceImpl;

import ch.ownz.s4m.sonos.discovery.ZonePlayerDiscoverer;

public class Engine {

	private static final Logger LOG = LoggerFactory.getLogger(Engine.class);

	private UpnpServiceImpl upnpService;
	private ZonePlayerDiscoverer zonePlayerDiscoverer;

	public void start() {
		LOG.info("Starting sonos4monkey engine");

		this.upnpService = new UpnpServiceImpl();
		this.zonePlayerDiscoverer = new ZonePlayerDiscoverer(this.upnpService);
		this.zonePlayerDiscoverer.discover();
	}

	public ZonePlayerDiscoverer getZonePlayerDiscoverer() {
		return this.zonePlayerDiscoverer;
	}

	public void stop() {
		LOG.info("Stopping sonos4monkey engine");
		GlobalScreen.unregisterNativeHook();
		this.zonePlayerDiscoverer.dispose();
		this.upnpService.shutdown();
	}

}
