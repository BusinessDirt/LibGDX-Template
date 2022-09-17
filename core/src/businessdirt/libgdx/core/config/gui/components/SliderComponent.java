package businessdirt.libgdx.core.config.gui.components;

import businessdirt.libgdx.core.config.data.PropertyData;
import businessdirt.libgdx.core.config.gui.SettingsGui;
import businessdirt.libgdx.core.config.gui.components.extras.GuiComponent;
import businessdirt.libgdx.core.util.Config;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

public class SliderComponent extends Slider implements GuiComponent {

    private final Label label;

    public SliderComponent(PropertyData property, Skin skin, float width, float height) {
        super(property.getProperty().min(), property.getProperty().max(), 1f, false, skin);
        this.setValue(property.getAsInt());
        this.setPosition(width - 50f * scale - this.getWidth() * this.getScaleX(), height - height / 2 - this.getHeight() * this.getScaleY() * 0.5f + 15f * scale);
        this.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (SettingsGui.get().getScrollPane() != null && actor.isTouchFocusTarget()) {
                    actor.getStage().unfocus(SettingsGui.get().getScrollPane());
                    property.setValue((int) getValue());
                    label.setText((int) getValue());
                    Config.getConfig().writeData();
                } else {
                    actor.getStage().setScrollFocus(SettingsGui.get().getScrollPane());
                }
            }
        });

        this.label = new Label(property.getPropertyValue().getValue(Config.getConfig()).toString(), skin);
        this.label.setAlignment(Align.center);
        this.label.setTouchable(Touchable.disabled);
        this.label.setPosition(width - 50f * scale - this.getWidth() * this.getScaleX(), height - height / 2 - this.getHeight() * this.getScaleY() * 0.5f - 15f * scale);
        this.label.setWidth(this.getWidth());
    }

    public Label getLabel() {
        return this.label;
    }
}
