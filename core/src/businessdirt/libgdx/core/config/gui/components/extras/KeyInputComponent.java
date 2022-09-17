package businessdirt.libgdx.core.config.gui.components.extras;

import businessdirt.libgdx.Template;
import businessdirt.libgdx.core.config.data.PropertyData;
import businessdirt.libgdx.core.config.data.types.Key;
import businessdirt.libgdx.core.config.gui.SettingsGui;
import businessdirt.libgdx.core.config.gui.components.KeyComponent;
import businessdirt.libgdx.core.util.Util;
import businessdirt.libgdx.ui.actors.FloatingMenu;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

public class KeyInputComponent extends FloatingMenu implements GuiComponent {

    private static KeyInputComponent instance;
    private final Label label;

    private PropertyData property;
    private KeyComponent component;
    private char type;

    public KeyInputComponent(Skin skin) {
        super(skin, 500f * scale, 500f * scale);

        this.label = new Label("Press a key to bind it", skin);
        this.label.setSize(500f * scale, 500f * scale);
        this.label.setFontScale(2f * scale);
        this.label.setWrap(true);
        this.label.setAlignment(Align.center);
        this.label.addListener(new InputListener() {
            @Override
            public boolean keyTyped(InputEvent event, char character) {
                if (KeyInputComponent.this.isVisible()) {
                    Key key = property.getAsKey();
                    if (type == 'p') {
                        key.setPrimary(event.getKeyCode());

                        String primaryChar = Util.getKeyCharFromCode(key.getPrimary());
                        if (primaryChar.length() == 1) primaryChar = " ".concat(primaryChar).concat(" ");
                        component.getPrimary().setText(primaryChar);
                    } else if (type == 's') {
                        key.setSecondary(event.getKeyCode());

                        String secondaryChar = Util.getKeyCharFromCode(key.getSecondary());
                        if (secondaryChar.length() == 1) secondaryChar = " ".concat(secondaryChar).concat(" ");
                        component.getSecondary().setText(secondaryChar);
                    }

                    property.setValue(key);
                    KeyInputComponent.this.setVisible(false);
                    Template.config.writeData();
                }

                return super.keyTyped(event, character);
            }
        });
        // set the keyboard focus to the label
        this.label.layout();

        this.addActor(this.label);
    }

    public void activate(KeyComponent component, PropertyData property, char type) {
        this.getStage().setKeyboardFocus(label);
        this.setVisible(true);

        this.component = component;
        this.property = property;
        this.type = type;
    }

    public static KeyInputComponent get() {
        KeyInputComponent.instance = new KeyInputComponent(SettingsGui.get().getSkin());
        return KeyInputComponent.instance;
    }
}
