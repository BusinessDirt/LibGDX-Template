package businessdirt.libgdx.core.config.gui.components;

import businessdirt.libgdx.core.config.data.PropertyData;
import businessdirt.libgdx.core.config.gui.components.extras.GuiComponent;
import businessdirt.libgdx.core.config.gui.components.extras.KeyInputComponent;
import businessdirt.libgdx.core.util.Util;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

public class KeyComponent extends Group implements GuiComponent {

    private final TextButton primary, secondary;

    public KeyComponent(PropertyData property, Skin skin, float width, float height) {
        super();
        float yOff = (height - 25f * scale) / 2 - GuiComponent.height;

        this.setSize(GuiComponent.width, height);
        this.setPosition(width - 50f * scale - GuiComponent.width, 0f);

        String primaryChar = Util.getKeyCharFromCode(property.getAsKey().getPrimary());
        if (primaryChar.length() == 1) primaryChar = " ".concat(primaryChar).concat(" ");

        this.primary = new TextButton(primaryChar, skin.get("settingsUI", TextButton.TextButtonStyle.class));
        this.primary.setSize(GuiComponent.width, GuiComponent.height);
        this.primary.setPosition(0f, height - yOff - GuiComponent.height);
        this.primary.getLabel().setWrap(true);
        this.primary.align(Align.center);
        this.primary.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                KeyInputComponent.get().activate(KeyComponent.this, property, 'p');
            }
        });

        String secondaryChar = Util.getKeyCharFromCode(property.getAsKey().getSecondary());
        if (secondaryChar.length() == 1) secondaryChar = " ".concat(secondaryChar).concat(" ");

        this.secondary = new TextButton(secondaryChar, skin.get("settingsUI", TextButton.TextButtonStyle.class));
        this.secondary.setSize(GuiComponent.width, GuiComponent.height);
        this.secondary.setPosition(0f, yOff);
        this.secondary.getLabel().setWrap(true);
        this.secondary.align(Align.center);
        this.secondary.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                KeyInputComponent.get().activate(KeyComponent.this, property, 's');
            }
        });

        this.addActor(this.primary);
        this.addActor(this.secondary);
    }

    public TextButton getPrimary() {
        return primary;
    }

    public TextButton getSecondary() {
        return secondary;
    }
}
