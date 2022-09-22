package businessdirt.libgdx.ui.screens;

import businessdirt.libgdx.Template;
import businessdirt.libgdx.core.config.gui.SettingsGui;
import businessdirt.libgdx.core.config.gui.components.extras.ColorPickerComponent;
import businessdirt.libgdx.core.config.gui.components.extras.KeyInputComponent;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

public class SettingsScreen extends AbstractScreen {

    private ScrollPane valuesPane;

    public SettingsScreen() {
        super(Template.assets.getSkin("skins/ui/8bit.json"), Color.TEAL);
    }

    @Override
    public void show() {
        float scale = Template.fullscreen.height / 1080f;
        float containerOffset = 5f * scale;

        // dimensions for the whole gui
        float width = 1520f * scale;
        float height = 760f * scale;

        // widths for categories and properties
        float categoryWidth = 300f * scale;
        float propertyWidth = 1220f * scale;

        // container for the scroll pane
        Table container = new Table(skin);
        container.setBackground("scrollPane");
        container.setBounds((Template.fullscreen.width - width) / 2f - containerOffset, (Template.fullscreen.height - height) / 2f - containerOffset,
                width + 2f * containerOffset, height + 2f * containerOffset);

        // table for all the config values
        Table propertyTable = new Table();
        propertyTable.align(Align.top);

        // table for all the config values
        Table categoryTable = new Table();
        categoryTable.align(Align.top);

        SettingsGui.init(categoryTable, propertyTable, categoryWidth , propertyWidth);

        // scroll pane for the config values
        this.valuesPane = new ScrollPane(propertyTable, skin.get("default", ScrollPane.ScrollPaneStyle.class));
        this.valuesPane.setSmoothScrolling(true);
        this.valuesPane.setScrollingDisabled(true, false);
        this.valuesPane.setFadeScrollBars(true);
        this.valuesPane.layout();

        // scroll pane for the categories
        ScrollPane categoriesPane = new ScrollPane(categoryTable, skin.get("default", ScrollPane.ScrollPaneStyle.class));
        categoriesPane.setSmoothScrolling(true);
        categoriesPane.setScrollingDisabled(true, false);
        categoriesPane.setFadeScrollBars(true);
        categoriesPane.setScrollBarPositions(true, false);
        categoriesPane.layout();

        // add everything to the container
        container.add(categoriesPane).width(categoryWidth).height(height).pad(containerOffset, containerOffset, containerOffset, 0f);
        container.add(this.valuesPane).width(propertyWidth).height(height).pad(containerOffset, 0f, containerOffset, containerOffset);
        SettingsGui.get().setScrollPane(this.valuesPane);
        this.stage.addActor(container);
        this.stage.setScrollFocus(this.valuesPane);

        // Settings Label
        Label settingsLabel = new Label("Settings", skin);
        settingsLabel.setAlignment(Align.bottomLeft);
        settingsLabel.setBounds(200f * scale, 950f * scale, 760f * scale, 140f * scale);
        settingsLabel.setFontScale(2f * scale);
        this.stage.addActor(settingsLabel);

        // Search Field
        TextField search = new TextField("", skin);
        search.setBounds((1720f - 350f) * scale, 950f * scale, 350f * scale, 50f * scale);
        search.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SettingsGui.get().setSearchQuery(propertyTable, propertyWidth, search.getText());
            }
        });
        this.stage.addActor(search);

        // Back Button
        TextButton backButton = new TextButton("Back", skin.get("backButton", TextButton.TextButtonStyle.class));
        backButton.setBounds((960f - 175f) * scale, 40f * scale, 350f * scale, 100f * scale);
        backButton.getLabel().setFontScale(2f * scale);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Template.get().setScreen(new MenuScreen());
            }
        });
        this.stage.addActor(backButton);

        this.stage.addActor(ColorPickerComponent.get());
        this.stage.addActor(KeyInputComponent.get());
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        this.valuesPane.setScrollbarsVisible(true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}
