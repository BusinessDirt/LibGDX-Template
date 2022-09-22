package businessdirt.libgdx.ui.actors.sequential;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

public class SequentialComponent<T extends Actor> extends Group {

    protected T component;
    protected boolean confirmed;
    protected InputCommand inputCommand;

    public SequentialComponent() {
        this.confirmed = false;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public void setInputCommand(InputCommand inputCommand) {
        this.inputCommand = inputCommand;
    }
}
