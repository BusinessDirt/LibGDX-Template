package businessdirt.libgdx.ui.actors;

import businessdirt.libgdx.Template;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class FloatingMenu extends Group {

    private final Vector2 position;

    public FloatingMenu(Skin skin, float x, float y, float width, float height) {
        super();
        this.position = new Vector2(0, 0);
        this.setBounds(0f, 0f, Template.fullscreen.width, Template.fullscreen.height);
        setBackgroundOpacity(0.4f);

        Button button = new Button(skin.get("blank", Button.ButtonStyle.class));
        button.setZIndex(1);
        button.setName("menu");
        button.setPosition(x, y);
        button.setSize(width, height);
        this.addActor(button);

        this.position.set(x, y);
        this.setVisible(false);
    }

    public FloatingMenu(Skin skin, float width, float height) {
        this(skin, (Template.fullscreen.width - width) / 2, (Template.fullscreen.height - height) / 2, width, height);
    }

    public void setBackgroundOpacity(float opacity) {
        // remove the previous background
        Actor background = this.findActor("background");
        if (background != null) this.removeActor(background);

        // generate a new pixmap with the given opacity
        Pixmap pixmap = new Pixmap(Template.fullscreen.width, Template.fullscreen.height, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(0f, 0f, 0f, opacity));
        pixmap.fill();

        // create a drawable and then an image of the pixmap
        Drawable drawable = new TextureRegionDrawable(new Texture(pixmap));
        Image drawableImage = new Image(drawable);
        drawableImage.setName("background");
        drawableImage.setZIndex(0);
        drawableImage.addListener(new BackgroundClickListener(this));

        // add the background to the group
        this.addActor(drawableImage);
    }

    @Override
    public void addActor(Actor actor) {
        actor.setPosition(this.position.x + actor.getX(), this.position.y + actor.getY());
        super.addActor(actor);
    }

    private static class BackgroundClickListener extends ClickListener {

        private final FloatingMenu floatingMenu;

        public BackgroundClickListener(FloatingMenu floatingMenu) {
            this.floatingMenu = floatingMenu;
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            this.floatingMenu.setVisible(false);
        }
    }
}
