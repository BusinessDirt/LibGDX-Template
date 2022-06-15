package businessdirt.libgdx.core.config.gui.components;

import businessdirt.libgdx.Template;
import businessdirt.libgdx.core.config.data.PropertyData;
import businessdirt.libgdx.core.config.gui.SettingsGui;
import businessdirt.libgdx.core.util.Config;
import businessdirt.libgdx.ui.actors.FloatingMenu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class ColorComponent extends GuiComponent {

    private Image color;

    public ColorComponent(PropertyData property, Skin skin, float width, float height) {
        ColorComponent instance = this;

        this.actor = new Button(skin.get("color", Button.ButtonStyle.class));
        Button button = (Button) this.actor;
        button.setTransform(true);
        button.setSize(76f, 76f);
        button.setPosition(width - 50f - (GuiComponent.width + this.actor.getWidth() * this.actor.getScaleX()) / 2, height - this.actor.getHeight() * this.actor.getScaleY() / 2 - height / 2);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ColorPicker.activate(instance, property);
            }
        });

        setColor(property.getAsColor());
        button.add(this.color).width(56).height(56).pad(10f);
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

    public static class ColorPicker {

        private final FloatingMenu picker;

        private static ColorPicker instance;
        private PropertyData property;
        private final TextField hexCode;
        private ColorComponent colorComponent;
        private final Slider alpha;
        private final Image colorWheel, colorWheelPicker;

        private ColorPicker(Skin skin) {
            this.picker = new FloatingMenu(skin, 500f, 500f);

            // color code in hex
            this.hexCode = new TextField("", skin);
            this.hexCode.setBounds(735f, 315f, 450f, 50f);
            this.hexCode.setAlignment(Align.center);
            this.hexCode.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    String content = hexCode.getText();

                    // check if the String starts with a #
                    if (!content.startsWith("#")) {
                        int position = hexCode.getCursorPosition();
                        content = "#" + content;
                        hexCode.setText(content);
                        hexCode.setCursorPosition(position + 1);
                    }

                    // remove any invalid characters
                    int position = hexCode.getCursorPosition();
                    content = content.replaceAll("[^#0123456789abcdef]", "");
                    hexCode.setText(content);
                    hexCode.setCursorPosition(Math.max(position, content.length() - 1));

                    // set the color to the input
                    if (content.length() == 9) {
                        Color color = property.getAsColor();
                        String[] colorChannels = content.replace("#", "").split("(?<=\\G.{" + 2 + "})");
                        color.set(Integer.decode("0x" + colorChannels[0]) / 255f, Integer.decode("0x" + colorChannels[1]) / 255f,
                                Integer.decode("0x" + colorChannels[2]) / 255f, Integer.decode("0x" + colorChannels[3]) / 255f);

                        alpha.setValue(Integer.decode("0x" + colorChannels[3]));
                        property.setValue(color);
                        colorComponent.setColor(color);
                        Config.getConfig().writeData();

                        ColorPicker.get().movePicker(color);
                    }

                    // remove any overflow chars
                    if (content.length() >= 10) {
                        position = hexCode.getCursorPosition();
                        hexCode.setText(String.valueOf(content.subSequence(0, 9)));
                        hexCode.setCursorPosition(Math.max(position, content.length() - 1));
                    }
                }
            });

            // Slider for Transparency
            this.alpha = new Slider(0, 255, 1, true, skin);
            this.alpha.setBounds(1135f, 390f, 50f, 375f);
            this.alpha.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    Color color = property.getAsColor();
                    color.a = alpha.getValue() / 255f;
                    property.setValue(color);
                    Config.getConfig().writeData();
                    colorComponent.setColor(property.getAsColor());
                    if (hexCode.getText().length() > 7) hexCode.setText(hexCode.getText().subSequence(0, 7) + toHex(alpha.getValue()));
                }
            });

            // Image for color picking
            this.colorWheel = new Image(Template.assets.getTexture("textures/gui/settings/colorwheel.png"));
            this.colorWheel.setBounds(735f, 390f, 375f, 375f);
            this.colorWheel.addListener(new ColorWheelClickListener());

            // Picker
            this.colorWheelPicker = new Image(skin, "pickerBlack");
            this.colorWheelPicker.setBounds(800f, 500f, 16f, 16f);

            this.picker.addActor(this.hexCode);
            this.picker.addActor(this.alpha);
            this.picker.addActor(this.colorWheel);
            this.picker.addActor(this.colorWheelPicker);
        }

        public static void activate(ColorComponent colorComponent, PropertyData property) {
            SettingsGui.get().getScrollPane().getStage().setScrollFocus(null);
            ColorPicker.get().picker.activate();

            ColorPicker.get().property = property;
            ColorPicker.get().colorComponent = colorComponent;

            Color color = property.getAsColor();
            ColorPicker.get().alpha.setValue(color.a * 255);

            String hex = toHex(color.r * 255) + toHex(color.g * 255) + toHex(color.b * 255) + toHex(color.a * 255);
            ColorPicker.get().hexCode.setText("#" + hex);

            // find the position of the color on the color wheel
            ColorPicker.get().movePicker(color);
        }

        private void movePicker(Color color) {
            Pixmap pixmap = new Pixmap(Gdx.files.internal("textures/gui/settings/colorwheel.png"));
            float scale = colorWheel.getWidth() / pixmap.getWidth();
            for (int x = 0; x < pixmap.getWidth(); x++) {
                for (int y = 0; y < pixmap.getHeight(); y++) {
                    Color pixelColor = new Color(pixmap.getPixel(x, y));
                    if (pixelColor.r == color.r && pixelColor.g == color.g && pixelColor.b == color.b) {
                        colorWheelPicker.setPosition(735f + x * scale - 8, 390f + (colorWheel.getHeight() - y * scale) - 8);
                    }
                }
            }
        }

        private static String toHex(float v) {
            String hex = Integer.toHexString((int) (v));
            if (hex.length() == 1) hex = "0" + hex;
            return hex;
        }

        public static void deactivate() {
            SettingsGui.get().getScrollPane().getStage().setScrollFocus(SettingsGui.get().getScrollPane());
            ColorPicker.get().picker.deactivate();
        }

        public void setColor(float x, float y) {
            Pixmap map = new Pixmap(Gdx.files.internal("assets/textures/gui/settings/colorwheel.png"));
            float scale = colorWheel.getWidth() / map.getWidth();

            int color = map.getPixel((int) (x / scale), map.getHeight() - (int) (y / scale));
            Color newColor = new Color(color);
            newColor.a = this.property.getAsColor().a;

            this.property.setValue(newColor);
            this.colorComponent.setColor(newColor);
            this.hexCode.setText("#" + toHex(newColor.r * 255) + toHex(newColor.g * 255) + toHex(newColor.b * 255) + toHex(newColor.a * 255));
            Config.getConfig().writeData();
        }

        public static ColorPicker get() {
            return ColorPicker.instance;
        }

        public static ColorPicker newInstance(Skin skin) {
            ColorPicker.instance = new ColorPicker(skin);
            return ColorPicker.instance;
        }

        public Group getActor() {
            return this.picker.getActor();
        }
    }

    private static class BackgroundClickListener extends ClickListener {

        @Override
        public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            ColorPicker.deactivate();
        }
    }

    private static class ColorWheelClickListener extends ClickListener {

        @Override
        public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            if (inBounds(x, y)) {
                ColorPicker.get().colorWheelPicker.setPosition(ColorPicker.get().colorWheel.getX() + x - ColorPicker.get().colorWheelPicker.getWidth() / 2,
                        ColorPicker.get().colorWheel.getY() + y - ColorPicker.get().colorWheelPicker.getHeight() / 2);
                ColorPicker.get().setColor(x, y);
            }
        }

        @Override
        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            super.touchDragged(event, x, y, pointer);
            if (inBounds(x, y)) {
                ColorPicker.get().colorWheelPicker.setPosition(ColorPicker.get().colorWheel.getX() + x - ColorPicker.get().colorWheelPicker.getWidth() / 2,
                        ColorPicker.get().colorWheel.getY() + y - ColorPicker.get().colorWheelPicker.getHeight() / 2);
                ColorPicker.get().setColor(x, y);
            }
        }

        private boolean inBounds(float x, float y) {
            if (x < 0 || x > ColorPicker.get().colorWheel.getWidth()) return false;
            if (y < 0 || y > ColorPicker.get().colorWheel.getHeight()) return false;
            return inRadius(x, y, ColorPicker.get().colorWheel.getWidth() / 2);
        }

        private boolean inRadius(float x, float y, float radius) {
            float xPos = radius - x;
            float yPos = radius - y;
            return Math.sqrt(xPos * xPos + yPos * yPos) < radius - 2f;
        }
    }
}
