package ch.ownz.s4m.sonos.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.teleal.cling.model.meta.RemoteService;

import ch.ownz.s4m.sonos.device.SonosDevice;

public class SonosServiceFactory {

	private static final Logger LOG = LoggerFactory.getLogger(SonosServiceFactory.class);

	public <T extends SonosService> T createService(Class<T> serviceClass, SonosDevice sonosDevice) {
		try {
			T sonosService = serviceClass.newInstance();
			SonosRemoteServiceReference remoteServiceReference = sonosService.getRemoteServiceReference();
			RemoteService remoteService = remoteServiceReference.lookupService(sonosDevice.getUpnpDevice());
			if (remoteService != null) {
				sonosService.init(remoteService, sonosDevice);
				return sonosService;
			}
		} catch (InstantiationException e) {
			LOG.error("Failed to create service", e);
		} catch (IllegalAccessException e) {
			LOG.error("Failed to create service", e);
		}
		return null;
	}

}
