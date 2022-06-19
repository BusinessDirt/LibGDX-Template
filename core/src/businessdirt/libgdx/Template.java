package businessdirt.libgdx;

import businessdirt.libgdx.core.util.AssetLoader;
import businessdirt.libgdx.core.util.Config;
import businessdirt.libgdx.ui.screens.LoadingScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Graphics;

import java.awt.*;

public class Template extends Game {

	public static Template instance;
	public static Config config;
	public static AssetLoader assets;

	public static Graphics.DisplayMode fullscreen;
	public static Dimension windowed;

	@Override
	public void create() {
		this.setScreen(new LoadingScreen());
	}

	public static Template get() {
		if (Template.instance == null) Template.instance = new Template();
		return Template.instance;
	}

	public static Template init(Graphics.DisplayMode fullscreen, Dimension windowed) {
		// set the fullscreen and windowed dimensions for later use
		Template.fullscreen = fullscreen;
		Template.windowed = windowed;
		return Template.get();
	}
}
