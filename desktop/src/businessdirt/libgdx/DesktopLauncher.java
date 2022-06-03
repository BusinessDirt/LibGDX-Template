package businessdirt.libgdx;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import businessdirt.libgdx.Template;

import java.awt.*;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("LibGDX Template");
		config.useVsync(true);
		config.setIdleFPS(5);

		float multiplier = 2f / 3f;
		Graphics.DisplayMode fullscreen = Lwjgl3ApplicationConfiguration.getDisplayMode();
		Dimension windowed = new Dimension((int) (fullscreen.width * multiplier), (int) (fullscreen.height * multiplier));
		config.setFullscreenMode(fullscreen);

		new Lwjgl3Application(Template.init(fullscreen, windowed), config);
	}
}
