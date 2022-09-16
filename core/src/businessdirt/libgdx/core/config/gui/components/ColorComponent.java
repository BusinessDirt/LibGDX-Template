package businessdirt.libgdx.core.config.gui.components;

import businessdirt.libgdx.core.config.data.PropertyData;
import businessdirt.libgdx.core.util.Config;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.kotcrab.vis.ui.widget.color.ColorPicker;
import com.kotcrab.vis.ui.widget.color.ColorPickerAdapter;

public class ColorComponent extends Button implements GuiComponent {

    private Image color;

    public ColorComponent(PropertyData propertyData, Skin skin, float width, float height) {
        super(skin.get("settingsUI", Button.ButtonStyle.class));
        this.setTransform(true);
        this.setSize(76f * scale, 76f * scale);
        this.setPosition(width - 50f * scale - (GuiComponent.width + this.getWidth() * this.getScaleX()) / 2, height - this.getHeight() * this.getScaleY() / 2 - height / 2);
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ColorPickerComponent.get().activate(ColorComponent.this, propertyData);
            }
        });

        this.setColor(propertyData.getAsColor());
        this.add(this.color).width(56f * scale).height(56f * scale).pad(10f * scale);
    }

    public void setColor(Color color) {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();

        if (this.color == null) {
            Drawable drawable = new TextureRegionDrawable(new Texture(pixmap));
            this.color = new Image(drawable);
            return;
        }

        this.color.setDrawable(new TextureRegionDrawable(new Texture(pixmap)));
    }

    public static class ColorPickerComponent extends Group {

        private static ColorPickerComponent instance;
        private ColorComponent colorComponent;
        private PropertyData propertyData;

        public ColorPickerComponent() {
            super();
            this.setVisible(false);
        }

        public void activate(ColorComponent colorComponent, PropertyData propertyData) {
            this.colorComponent = colorComponent;
            this.propertyData = propertyData;

            this.newColorPicker();
            this.setVisible(true);
        }

        public void newColorPicker() {
            ColorPicker picker = new ColorPicker("Pick Color");
            picker.setColor(this.propertyData.getAsColor());
            picker.setListener(new ColorPickerAdapter() {
                @Override
                public void finished(Color newColor) {
                    if (colorComponent == null) return;
                    colorComponent.setColor(newColor);

                    if (propertyData == null) return;
                    propertyData.setValue(newColor);
                    Config.getConfig().writeData();
                }
            });
            this.addActor(picker);
        }

        public static ColorPickerComponent get() {
            if (ColorPickerComponent.instance == null) ColorPickerComponent.instance = new ColorPickerComponent();
            return ColorPickerComponent.instance;
        }
    }
}
