package businessdirt.libgdx.ui.screens;

import businessdirt.libgdx.Template;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

public class MenuScreen extends AbstractScreen {

    public MenuScreen() {
        super(Template.assets.getSkin("skins/ui/skin.json"), Color.TEAL);
    }

    @Override
    public void show() {
        float scale = Template.fullscreen.height / 1080f;

        Table menuContainer = new Table();
        menuContainer.setBounds(50f * scale, 50f * scale, 350f * scale, 980f * scale);
        menuContainer.align(Align.top);

        TextButton settingsButton = new TextButton("Settings", this.skin.get("settingsButton", TextButton.TextButtonStyle.class));
        settingsButton.getLabel().setFontScale(2f * scale);
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Template.get().setScreen(new SettingsScreen());
            }
        });
        menuContainer.add(settingsButton).width(350f * scale).height(100f * scale).padBottom(5f * scale);
        menuContainer.row();

        TextButton playButton = new TextButton("Play", this.skin.get("playButton", TextButton.TextButtonStyle.class));
        playButton.setSize(350f * scale, 100f * scale);
        playButton.getLabel().setFontScale(2f * scale);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // set the play screen here
            }
        });
        menuContainer.add(playButton).width(350f * scale).height(100f * scale).padBottom(5f * scale);
        menuContainer.row();

        this.stage.addActor(menuContainer);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
