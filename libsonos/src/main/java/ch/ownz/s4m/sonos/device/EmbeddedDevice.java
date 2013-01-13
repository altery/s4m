package ch.ownz.s4m.sonos.device;

import java.util.Collections;
import java.util.List;

/**
 * Represents UPNP devices that are embedded into a
 * 
 * @author altery
 * 
 */
public abstract class EmbeddedDevice extends SonosDevice {

	@Override
	protected List<EmbeddedDevice> findEmbeddedDevices() {
		return Collections.emptyList();
	}

}
