package businessdirt.libgdx.ui.actors;

import businessdirt.libgdx.Template;
import businessdirt.libgdx.ui.actors.sequential.SequentialComponent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

public class SequentialFloatingMenu extends FloatingMenu {

    private final Array<SequentialComponent<?>> sequentialChildren;
    private int currentChild;

    public SequentialFloatingMenu(Skin skin, float x, float y, float width, float height) {
        super(skin, x, y, width, height);
        this.sequentialChildren = new Array<>();
        this.currentChild = 0;
    }

    public SequentialFloatingMenu(Skin skin, float width, float height) {
        this(skin, (Template.fullscreen.width - width) / 2, (Template.fullscreen.height - height) / 2, width, height);
    }

    public void add(SequentialComponent<?> actor) {
        this.addActor(actor);
        this.sequentialChildren.add(actor);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);

        if (this.sequentialChildren == null || this.sequentialChildren.size == 0) return;
        if (visible) {
            this.currentChild = 0;
            this.sequentialChildren.get(0).setVisible(true);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (this.sequentialChildren.size == 0) return;
        if (!this.isVisible()) return;
        if (this.sequentialChildren.size > this.currentChild + 1) {
            if (this.sequentialChildren.get(this.currentChild).isConfirmed()) {
                // set the clicked button invisible
                this.sequentialChildren.get(this.currentChild).setVisible(false);
                this.sequentialChildren.get(this.currentChild).setConfirmed(false);

                // set the next button visible
                this.currentChild++;
                this.sequentialChildren.get(this.currentChild).setVisible(true);
            }
        } else {
            if (this.sequentialChildren.get(this.currentChild).isConfirmed()) {
                this.sequentialChildren.get(this.currentChild).setVisible(false);
                this.sequentialChildren.get(this.currentChild).setConfirmed(false);
                this.setVisible(false);
            }
        }
    }
}
