package businessdirt.libgdx.ui.actors.sequential;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class SequentialTextButton extends SequentialComponent<TextButton> {

    public SequentialTextButton(String text, Skin skin) {
        this.setVisible(false);
        this.component = new TextButton(text, skin);
        this.component.setTransform(true);
        this.component.setSize(250f, 250f);
        this.component.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SequentialTextButton.this.setConfirmed(true);
                if (SequentialTextButton.this.inputCommand != null)
                    SequentialTextButton.this.inputCommand.execute();
            }
        });

        this.addActor(this.component);
    }
}
