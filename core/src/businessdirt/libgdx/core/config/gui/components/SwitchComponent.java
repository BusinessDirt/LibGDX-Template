package businessdirt.libgdx.core.config.gui.components;

import businessdirt.libgdx.core.config.data.PropertyData;
import businessdirt.libgdx.core.config.gui.components.extras.GuiComponent;
import businessdirt.libgdx.core.util.Config;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class SwitchComponent extends CheckBox implements GuiComponent {

    public SwitchComponent(PropertyData property, Skin skin, float width, float height) {
        super("", skin.get("square", CheckBox.CheckBoxStyle.class));
        this.setChecked(property.getAsBoolean());
        this.setTransform(true);
        this.setScale((75f * scale) / this.getHeight());
        this.setPosition(width - 50f * scale - (GuiComponent.width + this.getWidth() * this.getScaleX()) / 2, height - this.getHeight() * this.getScaleY() / 2 - height / 2);
        this.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                property.setValue(!property.getAsBoolean());
                Config.getConfig().writeData();
            }
        });
    }
}
