package businessdirt.libgdx.core.config.gui;

import businessdirt.libgdx.Template;
import businessdirt.libgdx.core.config.data.PropertyData;
import businessdirt.libgdx.core.config.gui.components.*;
import businessdirt.libgdx.game.util.Config;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;

public class SettingsGui {

    private ScrollPane pane;
    private final Skin skin;
    private static SettingsGui instance;

    private SettingsGui(Table table, float width) {

        this.skin = Template.assets.getSkin("skins/settings/skin.json");

        for (PropertyData property : Config.getConfig().getProperties()) {

            Label name = new Label(property.getProperty().name(), skin);
            name.setTouchable(Touchable.disabled);
            name.setFontScale(1.5f);
            name.layout();

            Label desc = new Label(property.getProperty().description(), skin);
            desc.setTouchable(Touchable.disabled);
            desc.setWrap(true);
            desc.setWidth(1000f);
            desc.setFontScale(1.1f);
            desc.layout();

            float h = Math.max(83f + name.getGlyphLayout().height + desc.getGlyphLayout().height, 125f);

            name.setPosition(35f, h - 25f - name.getGlyphLayout().height);
            desc.setPosition(45f, 25f + desc.getGlyphLayout().height / 2f);

            Group group = new Group();
            group.setSize(width, h);

            Button background = new Button(skin.get("blank", Button.ButtonStyle.class));
            background.setDisabled(false);
            background.setSize(width - 20f, h);
            background.setX(10f);

            group.addActor(background);
            group.addActor(name);
            group.addActor(desc);

            switch (property.getProperty().type()) {
                case SWITCH:
                    group.addActor(new SwitchComponent(property, skin, width, h).getActor());
                    break;
                case SLIDER:
                    SliderComponent sliderComponent = new SliderComponent(property, skin, width, h);
                    group.addActor(sliderComponent.getActor());
                    group.addActor(sliderComponent.getLabel());
                    break;
                case NUMBER:
                    group.addActor(new NumberComponent(property, skin, width, h).getActor());
                    break;
                case SELECTOR:
                    group.addActor(new SelectorComponent(property, skin, width, h).getActor());
                    break;
                case TEXT:
                    group.addActor(new TextComponent(property, skin, width, h).getActor());
                case PARAGRAPH:
                    group.addActor(new ParagraphComponent(property, skin, width, h).getActor());
                    break;
                case COLOR:
                    //group.addActor(new ColorComponent(property, skin, width, h).getActor());
                    break;
            }

            table.add(group).padBottom(5f).top().align(Align.right);
            table.row();
        }
    }

    public ScrollPane getScrollPane() {
        return this.pane;
    }

    public void setScrollPane(ScrollPane pane) {
        this.pane = pane;
    }

    public static SettingsGui get() {
        return SettingsGui.instance;
    }

    public static void newInstance(Table table, float width) {
        SettingsGui.instance = new SettingsGui(table, width);
    }
}
