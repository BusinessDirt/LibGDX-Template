package businessdirt.libgdx.core.config.gui.components;

import businessdirt.libgdx.core.config.data.PropertyData;
import businessdirt.libgdx.core.util.Config;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.google.common.base.Strings;

import java.util.Arrays;
import java.util.Objects;

public class NumberComponent extends GuiComponent {

    public NumberComponent(PropertyData property, Skin skin, float width, float height) {
        String previousEntry = String.valueOf(property.getAsDouble());
        this.actor = new TextField(previousEntry, skin);
        this.actor.setSize(GuiComponent.width, GuiComponent.height);
        this.actor.setPosition(width - 50f - (GuiComponent.width + this.actor.getWidth() * this.actor.getScaleX()) / 2, height - this.actor.getHeight() * this.actor.getScaleY() / 2 - height / 2);
        this.actor.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                TextField field = (TextField) actor;
                int cursorPosition = field.getCursorPosition();

                field.setText(field.getText().replaceAll("[^\\d.]", ""));
                field.setText(filterDots(field.getText()));

                field.setCursorPosition(Math.min(cursorPosition, field.getText().length()));
                if (!Objects.equals(field.getText(), "")) {
                    property.setValue(Double.parseDouble(field.getText()));
                    Config.getConfig().writeData();
                }
            }
        });
    }

    private String filterDots(String string) {
        char[] chars = string.toCharArray();
        boolean dot = false;

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '.' && !dot) {
                dot = true;
            } else if (chars[i] == '.' && dot) {
                chars[i] = '-';
            }
        }
        return String.valueOf(chars).replaceAll("-", "");
    }
}
