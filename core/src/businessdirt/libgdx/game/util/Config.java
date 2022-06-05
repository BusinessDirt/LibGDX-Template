package businessdirt.libgdx.game.util;

import businessdirt.libgdx.core.config.ConfigHandler;
import businessdirt.libgdx.core.config.data.Property;
import businessdirt.libgdx.core.config.data.PropertyType;
import com.badlogic.gdx.graphics.Color;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Config extends ConfigHandler {

    private static Config instance;

    @Property(
            type = PropertyType.SWITCH,
            name = "Switch Example",
            category = "Example Category",
            subcategory = "Example Subcategory",
            description = "A Switch has two state on and off."
    )
    public static boolean switchExample = false;

    @Property(
            type = PropertyType.TEXT,
            name = "Text Example",
            category = "Example Category",
            subcategory = "Example Subcategory",
            description = "A Text can receive input as any character."
    )
    public static String textExample = "";

    @Property(
            type = PropertyType.PARAGRAPH,
            name = "Paragraph Example",
            category = "Example Category",
            subcategory = "Example Subcategory",
            description = "A Paragraph can only receive characters from a-z as input."
    )
    public static String paragraphExample = "";

    @Property(
            type = PropertyType.NUMBER,
            name = "Number Example",
            category = "Example Category",
            subcategory = "Example Subcategory 2",
            description = "A Number can only receive numbers as input."
    )
    public static double numberExample = 0D;

    @Property(
            type = PropertyType.SLIDER,
            name = "Slider Example",
            category = "Example Category 2",
            description = "A Slider ranges from the specified min() value to the specified max() value.",
            max = 100
    )
    public static int sliderExample = 25;

    @Property(
            type = PropertyType.COLOR,
            name = "Color Example",
            category = "Example Category",
            subcategory = "Example Subcategory 2",
            description = "A Color is just a color picker."
    )
    public static Color colorExample = new Color(1f, 1f, 1f, 1f);

    @Property(
            type = PropertyType.SELECTOR,
            name = "Selector Example",
            category = "Example Category 2",
            description = "Example Description",
            options = {"lmao", "rofl", "haha", "69", "420"}
    )
    public static String selectorExample = "";

    public Config() {
        super(new File(Util.getConfigFolder(), "\\config.toml"));
        this.initialize();
    }

    public static Config getConfig() {
        if (Config.instance == null) Config.instance = new Config();
        return Config.instance;
    }
}
