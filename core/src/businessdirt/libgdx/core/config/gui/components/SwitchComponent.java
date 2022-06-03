package businessdirt.libgdx.core.config.gui.components;

import businessdirt.libgdx.core.config.data.PropertyData;
import businessdirt.libgdx.game.util.Config;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class SwitchComponent extends GuiComponent {

    public SwitchComponent(PropertyData property, Skin skin, float width, float height) {
        this.actor = new CheckBox("", skin.get("square", CheckBox.CheckBoxStyle.class));
        CheckBox checkBox = (CheckBox) this.actor;

        checkBox.setChecked(property.getAsBoolean());
        checkBox.setTransform(true);
        checkBox.setScale(75f / this.actor.getHeight());
        checkBox.setPosition(width - 50f - (GuiComponent.width + this.actor.getWidth() * this.actor.getScaleX()) / 2, height - this.actor.getHeight() * this.actor.getScaleY() / 2 - height / 2);
        checkBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                property.setValue(!property.getAsBoolean());
                Config.getConfig().writeData();
            }
        });
    }
}
