package businessdirt.libgdx.core.config.gui.components;

import businessdirt.libgdx.core.config.data.PropertyData;
import businessdirt.libgdx.core.config.gui.components.extras.GuiComponent;
import businessdirt.libgdx.core.util.Config;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.Objects;

public class NumberComponent extends TextField implements GuiComponent {

    public NumberComponent(PropertyData property, Skin skin, float width, float height) {
        super(String.valueOf(property.getAsDouble()), skin);
        this.setSize(GuiComponent.width, GuiComponent.height);
        this.setPosition(width - 50f * scale - (GuiComponent.width + this.getWidth() * this.getScaleX()) / 2, height - this.getHeight() * this.getScaleY() / 2 - height / 2);
        this.setTextFieldFilter(((textField, c) -> Character.isDigit(c) || c == '.'));
        this.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                filter();
                if (!Objects.equals(getText(), "")) {
                    property.setValue(Double.parseDouble(getText()));
                    Config.getConfig().writeData();
                }
            }
        });
    }

    private void filter() {
        char[] chars = this.getText().toCharArray();
        boolean dot = false;
        int cursorPosition = getCursorPosition();

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '.' && !dot) {
                dot = true;
            } else if (chars[i] == '.' && dot) {
                chars[i] = '-';
            }
        }
        this.setText(String.valueOf(chars).replaceAll("-", ""));
        setCursorPosition(Math.min(cursorPosition, getText().length()));
    }
}
