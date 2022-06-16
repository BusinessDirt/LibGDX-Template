package businessdirt.libgdx.core.config.gui.components;

import businessdirt.libgdx.core.config.data.PropertyData;
import businessdirt.libgdx.ui.actors.FloatingMenu;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

public class KeyComponent extends GuiComponent {

    public KeyComponent(PropertyData property, Skin skin, float width, float height) {
        KeyComponent instance = this;

        this.actor = new TextButton(String.valueOf(property.getAsInt()), skin);
        TextButton button = (TextButton) this.actor;
        button.setSize(75f, 75f);
        button.setPosition(width - 50f - (GuiComponent.width + this.actor.getWidth() * this.actor.getScaleX()) / 2, height - this.actor.getHeight() * this.actor.getScaleY() / 2 - height / 2);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                KeyInputHandler.get().activate(instance, property);
            }
        });
    }

    public static class KeyInputHandler {

        private final FloatingMenu menu;
        private static KeyInputHandler instance;

        public KeyInputHandler(Skin skin) {
            this.menu = new FloatingMenu(skin, 500f, 500f);

            Label label = new Label("Press a key to bind it", skin);
            label.setSize(500f, 500f);
            label.setFontScale(2f);
            label.setWrap(true);
            label.setAlignment(Align.center);
            label.layout();

            this.menu.addActor(label);
        }

        public void activate(KeyComponent keyComponent, PropertyData data) {
            this.menu.activate();
        }

        public Group getActor() {
            return this.menu.getActor();
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
