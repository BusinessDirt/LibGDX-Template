package businessdirt.libgdx.core.config.gui.components;

import businessdirt.libgdx.Template;
import businessdirt.libgdx.core.config.data.PropertyData;
import businessdirt.libgdx.core.config.data.types.Key;
import businessdirt.libgdx.core.util.Util;
import businessdirt.libgdx.ui.actors.FloatingMenu;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
                KeyInputHandler.get().activate(KeyComponent.this, property, 'p');
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
                KeyInputHandler.get().activate(KeyComponent.this, property, 's');
            }
        });

        this.addActor(this.primary);
        this.addActor(this.secondary);
    }

    public static class KeyInputHandler extends FloatingMenu {

        private static KeyInputHandler instance;
        private final Label label;

        private PropertyData property;
        private KeyComponent component;
        private char type;

        public KeyInputHandler(Skin skin) {
            super(skin, 500f * scale, 500f * scale);

            this.label = new Label("Press a key to bind it", skin);
            this.label.setSize(500f * scale, 500f * scale);
            this.label.setFontScale(2f * scale);
            this.label.setWrap(true);
            this.label.setAlignment(Align.center);
            this.label.addListener(new InputListener() {
                @Override
                public boolean keyTyped(InputEvent event, char character) {
                    if (KeyInputHandler.this.isVisible()) {
                        Key key = property.getAsKey();
                        if (type == 'p') {
                            key.setPrimary(event.getKeyCode());

                            String primaryChar = Util.getKeyCharFromCode(key.getPrimary());
                            if (primaryChar.length() == 1) primaryChar = " ".concat(primaryChar).concat(" ");
                            component.primary.setText(primaryChar);
                        } else if (type == 's') {
                            key.setSecondary(event.getKeyCode());

                            String secondaryChar = Util.getKeyCharFromCode(key.getSecondary());
                            if (secondaryChar.length() == 1) secondaryChar = " ".concat(secondaryChar).concat(" ");
                            component.secondary.setText(secondaryChar);
                        }

                        property.setValue(key);
                        KeyInputHandler.this.setVisible(false);
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

        public static KeyInputHandler newInstance(Skin skin) {
            KeyInputHandler.instance = new KeyInputHandler(skin);
            return KeyInputHandler.instance;
        }

        public static KeyInputHandler get() {
            return KeyInputHandler.instance;
        }
    }
}
