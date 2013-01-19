package ch.ownz.s4m;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import ch.ownz.s4m.sonos.device.ZonePlayerDevice;
import ch.ownz.s4m.sonos.model.Entry;
import ch.ownz.s4m.sonos.model.PlayMode;
import ch.ownz.s4m.sonos.service.avtransport.AVTransportService;

/**
 * Test entry point that serves as a quick and dirty way to get things started.
 * 
 * @author altery
 * 
 */
public class Main {

	private final Engine engine;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Main();

	}

	public Main() {
		this.engine = new Engine();
		this.engine.start();
		registerGlobalKeyListener();
	}

	private void registerGlobalKeyListener() {
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());

			System.exit(1);
		} finally {

		}
		GlobalScreen.getInstance().addNativeKeyListener(new KeyListener());
	}

	private class KeyListener implements NativeKeyListener {

		@Override
		public void nativeKeyPressed(NativeKeyEvent arg0) {
			System.out.println("Key pressed: " + arg0.getKeyCode());
			int keyCode = arg0.getKeyCode();
			switch (keyCode) {
			case 8:
				Main.this.engine.stop();
				break;

			case 107:
				ZonePlayerDevice zonePlayer = Main.this.engine.getZonePlayerDiscoverer().getZonePlayerByName(
						"Tragbar (Play:5)");
				if (zonePlayer != null) {
					int volume = zonePlayer.getRenderingControlService().getVolume();
					volume += 5;
					zonePlayer.getRenderingControlService().setVolume(volume);
				}
				break;

			case 109:
				zonePlayer = Main.this.engine.getZonePlayerDiscoverer().getZonePlayerByName("Tragbar (Play:5)");
				if (zonePlayer != null) {
					int volume = zonePlayer.getRenderingControlService().getVolume();
					volume -= 5;
					zonePlayer.getRenderingControlService().setVolume(volume);
				}
				break;
			case 96:
				zonePlayer = Main.this.engine.getZonePlayerDiscoverer().getZonePlayerByName("Tragbar (Play:5)");
				if (zonePlayer != null) {
					AVTransportService avTransportService = zonePlayer.getMediaRendererDevice().getAvTransportService();
					avTransportService.stop();
				}
				break;
			case 97:
				zonePlayer = Main.this.engine.getZonePlayerDiscoverer().getZonePlayerByName("Tragbar (Play:5)");
				if (zonePlayer != null) {
					AVTransportService avTransportService = zonePlayer.getMediaRendererDevice().getAvTransportService();
					Entry entry = new Entry("F00090020s10049", "Radio 1 93.6 (Adult Contemporary)", "L", "", "", "",
							"", "x-sonosapi-stream:s10049?sid=254&amp;flags=32", 0, "");
					avTransportService.setAvTransportUri(entry);
					avTransportService.setPlayMode(PlayMode.NORMAL);
					zonePlayer.getRenderingControlService().setMute(false);
					avTransportService.play();
				}
				break;
			}
		}

		@Override
		public void nativeKeyReleased(NativeKeyEvent arg0) {
		}

		@Override
		public void nativeKeyTyped(NativeKeyEvent arg0) {
		}

	}

}
