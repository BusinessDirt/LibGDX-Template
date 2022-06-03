package businessdirt.libgdx.core.config.gui.components;

import businessdirt.libgdx.core.config.data.PropertyData;
import businessdirt.libgdx.core.config.gui.SettingsGui;
import businessdirt.libgdx.game.util.Config;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

public class SliderComponent extends GuiComponent {

    private final Label label;

    public SliderComponent(PropertyData property, Skin skin, float width, float height) {
        this.actor = new Slider(property.getProperty().min(), property.getProperty().max(), 1f, false, skin);
        Slider slider = (Slider) this.actor;

        slider.setValue((float) (int) property.getValue().getValue(Config.getConfig()));
        slider.setPosition(width - 50f - this.actor.getWidth() * this.actor.getScaleX(), height - height / 2 - this.actor.getHeight() * this.actor.getScaleY() * 0.5f + 15f);
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (SettingsGui.get().getScrollPane() != null && actor.isTouchFocusTarget()) {
                    actor.getStage().unfocus(SettingsGui.get().getScrollPane());
                    property.setValue((int) slider.getValue());
                    label.setText((int) slider.getValue());
                    Config.getConfig().writeData();
                } else {
                    actor.getStage().setScrollFocus(SettingsGui.get().getScrollPane());
                }
            }
        });

        this.label = new Label(property.getValue().getValue(Config.getConfig()).toString(), skin);
        this.label.setAlignment(Align.center);
        this.label.setTouchable(Touchable.disabled);
        this.label.setPosition(width - 50f - this.actor.getWidth() * this.actor.getScaleX(), height - height / 2 - this.actor.getHeight() * this.actor.getScaleY() * 0.5f - 15f);
        this.label.setWidth(this.actor.getWidth());
    }

    public Label getLabel() {
        return this.label;
    }
}
