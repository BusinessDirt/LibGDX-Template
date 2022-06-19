package businessdirt.libgdx.core.config.gui.components;

import businessdirt.libgdx.Template;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class GuiComponent {

    protected Actor actor;

    protected static final float scale = Template.fullscreen.height / 1080f;
    public static final float width = 140f * scale, height = 35f * scale;

    public Actor getActor() {
        return this.actor;
    }
}
