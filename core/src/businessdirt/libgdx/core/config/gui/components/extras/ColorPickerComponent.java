package businessdirt.libgdx.core.config.gui.components.extras;

import businessdirt.libgdx.core.config.data.PropertyData;
import businessdirt.libgdx.core.config.gui.components.ColorComponent;
import businessdirt.libgdx.core.util.Config;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.kotcrab.vis.ui.widget.color.ColorPicker;
import com.kotcrab.vis.ui.widget.color.ColorPickerAdapter;

public class ColorPickerComponent extends Group {

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
