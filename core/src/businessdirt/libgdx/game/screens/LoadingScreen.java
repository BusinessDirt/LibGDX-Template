package businessdirt.libgdx.game.screens;

import businessdirt.libgdx.Template;
import businessdirt.libgdx.game.util.AssetLoader;
import businessdirt.libgdx.game.util.Config;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

public class LoadingScreen extends ScreenAdapter {

    public LoadingScreen() {
        Template.config = Config.getConfig();

        Template.assets = new AssetLoader();
        Template.assets.load();
    }

    @Override
    public void render(float delta) {
        // clear the screen from the previous frame
        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        // if the assets finished loading it will enter the main menu
        if (Template.assets.update()) Template.get().setScreen(new MenuScreen());
    }

    @Override
    public void hide() {
        this.dispose();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
