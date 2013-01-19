package ch.ownz.s4m.sonos.service.alarmclock;

import org.teleal.cling.model.gena.GENASubscription;
import org.teleal.cling.model.meta.RemoteService;

import ch.ownz.s4m.sonos.service.SonosRemoteServiceReference;
import ch.ownz.s4m.sonos.service.SonosService;

public class AlarmClockService extends SonosService {

	@Override
	protected void handleServiceEvent(GENASubscription<RemoteService> subscription) {
	}

	@Override
	protected SonosRemoteServiceReference getRemoteServiceReference() {
		return SonosRemoteServiceReference.getAlarmClockServiceReference();
	}

}
