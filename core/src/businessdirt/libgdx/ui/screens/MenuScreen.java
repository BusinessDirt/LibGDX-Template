package businessdirt.libgdx.ui.screens;

import businessdirt.libgdx.Template;
import businessdirt.libgdx.ui.actors.SequentialFloatingMenu;
import businessdirt.libgdx.ui.actors.sequential.SequentialTextButton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

public class MenuScreen extends AbstractScreen {

    public MenuScreen() {
        super(Template.assets.getSkin("skins/ui/8bit.json"), Color.TEAL);
    }

    @Override
    public void show() {
        float scale = Template.fullscreen.height / 1080f;

        SequentialFloatingMenu sequentialFloatingMenu = new SequentialFloatingMenu(skin, 500f, 500f);
        sequentialFloatingMenu.add(new SequentialTextButton("test", skin));
        SequentialTextButton test2 = new SequentialTextButton("test2", skin);
        test2.setPosition(250f, 250f);
        sequentialFloatingMenu.add(test2);

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

        TextButton testButton = new TextButton("Test", this.skin.get("playButton", TextButton.TextButtonStyle.class));
        testButton.setSize(350f * scale, 100f * scale);
        testButton.getLabel().setFontScale(2f * scale);
        testButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sequentialFloatingMenu.setVisible(true);
            }
        });
        menuContainer.add(testButton).width(350f * scale).height(100f * scale).padBottom(5f * scale);
        menuContainer.row();

        TextButton quitButton = new TextButton("Quit", this.skin.get("quitButton", TextButton.TextButtonStyle.class));
        quitButton.setSize(350f * scale, 100f * scale);
        quitButton.getLabel().setFontScale(2f * scale);
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
                System.exit(0);
            }
        });
        menuContainer.add(quitButton).width(350f * scale).height(100f * scale).padBottom(5f * scale);
        menuContainer.row();

        this.stage.addActor(menuContainer);
        this.stage.addActor(sequentialFloatingMenu);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
