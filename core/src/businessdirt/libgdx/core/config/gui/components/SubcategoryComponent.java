package businessdirt.libgdx.core.config.gui.components;

import businessdirt.libgdx.core.config.gui.components.extras.GuiComponent;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class SubcategoryComponent extends Group implements GuiComponent {

    public SubcategoryComponent(String subcategory, Skin skin, float width) {
        super();
        this.setBounds(0, 0f, width, 75f * scale);

        Button line = new Button(skin.get("divider", Button.ButtonStyle.class));
        line.setBounds(15f * scale, 15f * scale, width - 30f * scale, 5f * scale);
        line.setTouchable(Touchable.disabled);

        Label label = new Label(subcategory, skin);
        label.setFontScale(1.75f * scale);
        label.setPosition(35f * scale, 22f * scale);
        label.setColor(skin.getColor("checked"));

        this.addActor(line);
        this.addActor(label);
    }
}
