package ch.ownz.s4m.sonos.service.renderingcontrol;

import org.teleal.cling.model.action.ActionInvocation;
import org.teleal.cling.model.meta.RemoteService;
import org.teleal.cling.model.types.UnsignedIntegerFourBytes;
import org.teleal.cling.model.types.UnsignedIntegerTwoBytes;

import ch.ownz.s4m.action.NoResponseServiceAction;
import ch.ownz.s4m.sonos.model.Channel;
import ch.ownz.s4m.sonos.service.SonosService;

public class SetVolumeAction extends NoResponseServiceAction {

	private int value;

	private int instanceId = 0;

	private Channel channel = Channel.Master;

	public SetVolumeAction(SonosService service, int volume) {
		super(service);
		setVolume(volume);
	}

	public void setInstanceId(int id) {
		if (id < 0) {
			throw new IllegalArgumentException("Id must not be negative");
		}
		this.instanceId = id;
	}

	public void setVolume(int value) {
		if (value < 0) {
			value = 0;
		}
		if (value > 100) {
			value = 100;
		}
		this.value = value;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	@Override
	protected String getActionName() {
		return "SetVolume";
	}

	@Override
	protected void fillInput(ActionInvocation<RemoteService> actionInvocation) {
		actionInvocation.setInput("InstanceID", new UnsignedIntegerFourBytes(this.instanceId));
		actionInvocation.setInput("Channel", this.channel.toString());
		actionInvocation.setInput("DesiredVolume", new UnsignedIntegerTwoBytes(this.value));
	}
}
