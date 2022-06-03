package businessdirt.libgdx.game.util;

import businessdirt.libgdx.Template;
import com.badlogic.gdx.Gdx;

public class Input {

    public static void defaultInputHandler() {
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.ALT_LEFT) && Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.F4)) {
            Gdx.app.exit();
            System.exit(0);
        }

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.F11)) {
            if (Gdx.graphics.isFullscreen()) {
                Gdx.graphics.setWindowedMode(Template.windowed.width, Template.windowed.height);
            } else {
                Gdx.graphics.setFullscreenMode(Template.fullscreen);
            }
        }
    }
}
