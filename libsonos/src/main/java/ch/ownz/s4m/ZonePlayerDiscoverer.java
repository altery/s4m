package ch.ownz.s4m;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.teleal.cling.UpnpService;
import org.teleal.cling.model.message.header.UDADeviceTypeHeader;
import org.teleal.cling.model.meta.RemoteDevice;
import org.teleal.cling.model.types.UDADeviceType;
import org.teleal.cling.registry.DefaultRegistryListener;
import org.teleal.cling.registry.Registry;
import org.teleal.cling.registry.RegistryListener;

import ch.ownz.s4m.sonos.device.DeviceFactory;
import ch.ownz.s4m.sonos.device.ZonePlayerDevice;

public class ZonePlayerDiscoverer {

	private static final Logger LOG = LoggerFactory.getLogger(ZonePlayerDiscoverer.class);

	private static final String ZONE_PLAYER_DEVICE_TYPE = "ZonePlayer";

	private final Map<String, ZonePlayerDevice> zonePlayers = new ConcurrentHashMap<String, ZonePlayerDevice>();

	private final UpnpService upnpService;

	private final RegistryListener zonePlayerListener = new DefaultRegistryListener() {

		@Override
		public void remoteDeviceUpdated(Registry registry, RemoteDevice device) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Detected remote device update " + device);
			}
			ZonePlayerDevice zonePlayer = ZonePlayerDiscoverer.this.zonePlayers.get(getKey(device));
			if (zonePlayer != null) {
				zonePlayer.deviceUpdated();
			}
		}

		@Override
		public void remoteDeviceRemoved(Registry registry, RemoteDevice device) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Detected remote device removal " + device);
			}
			ZonePlayerDevice zonePlayer = ZonePlayerDiscoverer.this.zonePlayers.remove(getKey(device));
			if (zonePlayer != null) {
				zonePlayer.dispose();
				if (LOG.isDebugEnabled()) {
					LOG.debug("Zone player disappeared: " + zonePlayer);
				}
			}
		}

		@Override
		public void remoteDeviceAdded(Registry registry, RemoteDevice device) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Detected remote device addition " + device);
			}
			boolean sonosProduct = device.getDetails().getManufacturerDetails().getManufacturer().toUpperCase()
					.contains("SONOS");
			if (sonosProduct) {
				String key = getKey(device);
				if (!ZonePlayerDiscoverer.this.zonePlayers.containsKey(key)) {
					ZonePlayerDevice zonePlayer = ZonePlayerDiscoverer.this.deviceFactory
							.createZonePlayerDevice(device);
					if (zonePlayer != null) {
						ZonePlayerDevice replacedPlayer = ZonePlayerDiscoverer.this.zonePlayers.put(key, zonePlayer);
						if (replacedPlayer != null) {
							replacedPlayer.dispose();
						}
						if (LOG.isDebugEnabled()) {
							LOG.debug("Found zone new player: " + zonePlayer + ", we now have "
									+ ZonePlayerDiscoverer.this.zonePlayers.size() + " devices");
						}
					}
				}
			}
		}

		private String getKey(RemoteDevice device) {
			return device.getIdentity().getUdn().getIdentifierString();
		}
	};

	private final DeviceFactory deviceFactory;

	public ZonePlayerDiscoverer(UpnpService upnpService) {
		this.upnpService = upnpService;
		this.deviceFactory = new DeviceFactory(upnpService.getControlPoint());
		registerListener();
	}

	public ZonePlayerDevice getZonePlayerByName(String zoneName) {
		for (Entry<String, ZonePlayerDevice> entry : this.zonePlayers.entrySet()) {
			ZonePlayerDevice zonePlayer = entry.getValue();
			String zonePlayerZoneName = zonePlayer.getDevicePropertiesService().getZoneAttributes().getName();
			if (zoneName.equalsIgnoreCase(zonePlayerZoneName)) {
				return zonePlayer;
			}
		}
		return null;
	}

	public List<ZonePlayerDevice> getAllZonePlayers() {
		return new ArrayList<ZonePlayerDevice>(this.zonePlayers.values());
	}

	public void discover() {
		final UDADeviceType udaType = new UDADeviceType(ZONE_PLAYER_DEVICE_TYPE);
		this.upnpService.getControlPoint().search(new UDADeviceTypeHeader(udaType));
	}

	public void dispose() {
		removeListener();
		List<ZonePlayerDevice> allZonePlayers = getAllZonePlayers();
		for (ZonePlayerDevice zonePlayerDevice : allZonePlayers) {
			zonePlayerDevice.dispose();
		}
	}

	private void registerListener() {
		this.upnpService.getRegistry().addListener(this.zonePlayerListener);
	}

	private void removeListener() {
		this.upnpService.getRegistry().removeListener(this.zonePlayerListener);
	}
}
