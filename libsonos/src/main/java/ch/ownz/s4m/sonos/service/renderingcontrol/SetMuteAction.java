package ch.ownz.s4m.sonos.service.renderingcontrol;

import org.teleal.cling.model.action.ActionInvocation;
import org.teleal.cling.model.meta.RemoteService;
import org.teleal.cling.model.types.UnsignedIntegerFourBytes;

import ch.ownz.s4m.action.NoResponseServiceAction;
import ch.ownz.s4m.sonos.model.Channel;
import ch.ownz.s4m.sonos.service.SonosService;

public class SetMuteAction extends NoResponseServiceAction {

	private boolean mute;

	private int instanceId = 0;

	private Channel channel = Channel.Master;

	public SetMuteAction(SonosService service, boolean mute) {
		super(service);
		setMute(mute);
	}

	@Override
	protected String getActionName() {
		return "SetMute";
	}

	public void setInstanceId(int id) {
		if (id < 0) {
			throw new IllegalArgumentException("Id must not be negative");
		}
		this.instanceId = id;
	}

	public void setMute(boolean mute) {
		this.mute = mute;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	@Override
	protected void fillInput(ActionInvocation<RemoteService> actionInvocation) {
		actionInvocation.setInput("InstanceID", new UnsignedIntegerFourBytes(this.instanceId));
		actionInvocation.setInput("Channel", this.channel.toString());
		actionInvocation.setInput("DesiredMute", this.mute);

	}

}
