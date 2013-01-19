package ch.ownz.s4m.sonos.service.audioin;

import org.teleal.cling.model.gena.GENASubscription;
import org.teleal.cling.model.meta.RemoteService;

import ch.ownz.s4m.sonos.service.SonosRemoteServiceReference;
import ch.ownz.s4m.sonos.service.SonosService;

public class AudioInService extends SonosService {

	@Override
	protected void handleServiceEvent(GENASubscription<RemoteService> subscription) {
		// TODO Auto-generated method stub

	}

	@Override
	protected SonosRemoteServiceReference getRemoteServiceReference() {
		return SonosRemoteServiceReference.getAudioInServiceReference();
	}

}
