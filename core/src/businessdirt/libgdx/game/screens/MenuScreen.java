package businessdirt.libgdx.game.screens;

import businessdirt.libgdx.Template;
import businessdirt.libgdx.game.util.Input;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

public class MenuScreen extends AbstractScreen {

    public MenuScreen() {
        super(Template.assets.getSkin("skins/mainmenu/skin.json"), Color.TEAL);
    }

    @Override
    public void show() {
        Table menuContainer = new Table();
        menuContainer.setBounds(50f, 50f, 350f, 980f);
        menuContainer.align(Align.top);

        TextButton settingsButton = new TextButton("Settings", this.skin.get("settingsButton", TextButton.TextButtonStyle.class));
        settingsButton.getLabel().setFontScale(2f);
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Template.get().setScreen(new SettingsScreen());
            }
        });
        menuContainer.add(settingsButton).width(350f).height(100f).padBottom(5f);
        menuContainer.row();

        TextButton playButton = new TextButton("Play", this.skin.get("playButton", TextButton.TextButtonStyle.class));
        playButton.setSize(350f, 100f);
        playButton.getLabel().setFontScale(2f);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // set the play screen here
            }
        });
        menuContainer.add(playButton).width(350f).height(100f).padBottom(5f);
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
