package businessdirt.libgdx;

import businessdirt.libgdx.game.screens.LoadingScreen;
import businessdirt.libgdx.game.screens.MenuScreen;
import businessdirt.libgdx.game.screens.SettingsScreen;
import businessdirt.libgdx.game.util.AssetLoader;
import businessdirt.libgdx.game.util.Config;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

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
		Template.fullscreen = fullscreen;
		Template.windowed = windowed;
		return Template.get();
	}
}
