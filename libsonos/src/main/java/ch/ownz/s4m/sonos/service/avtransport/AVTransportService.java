package ch.ownz.s4m.sonos.service.avtransport;

import org.teleal.cling.model.gena.GENASubscription;
import org.teleal.cling.model.meta.RemoteService;

import ch.ownz.s4m.sonos.model.Entry;
import ch.ownz.s4m.sonos.model.PlayMode;
import ch.ownz.s4m.sonos.service.SonosRemoteServiceReference;
import ch.ownz.s4m.sonos.service.SonosService;

public class AVTransportService extends SonosService {

	/**
	 * Sets the input to the given entry without modifing the queue.
	 * 
	 * @param entry
	 *            the entry
	 */
	public void setAvTransportUri(Entry entry) {
		new SetTransportUri(this, entry).execute();
	}

	/**
	 * Starts the playback of the current transport uri.
	 */
	public void play() {
		new PlayAction(this).execute();
	}

	/**
	 * Stops the playback
	 */
	public void stop() {
		new StopAction(this).execute();
	}

	/**
	 * Pauses the playback
	 */
	public void pause() {
		new PauseAction(this).execute();
	}

	/**
	 * Sets the play mode.
	 * 
	 * @param mode
	 */
	public void setPlayMode(PlayMode mode) {
		new SetPlayModeAction(this, mode).execute();
	}

	@Override
	protected void handleServiceEvent(GENASubscription<RemoteService> subscription) {
	}

	@Override
	protected SonosRemoteServiceReference getRemoteServiceReference() {
		return SonosRemoteServiceReference.getAvTransportServiceReference();
	}

}
