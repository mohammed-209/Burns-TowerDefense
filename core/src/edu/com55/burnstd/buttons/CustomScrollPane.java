package edu.com55.burnstd.buttons;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;

public class CustomScrollPane extends ScrollPane {

    private Rectangle scissorBounds; // Store the scissor bounds for reuse

    public CustomScrollPane(Actor widget, Skin skin) {
        super(widget, skin);
        scissorBounds = new Rectangle();
    }

   

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Calculate the scissor bounds relative to the stage
        scissorBounds.set(getX(), getY(), getWidth(), getHeight());

        // Store the current clip to restore later
        ScissorStack.calculateScissors(getStage().getCamera(), batch.getTransformMatrix(), scissorBounds, scissorBounds);
        ScissorStack.pushScissors(scissorBounds);

        // Draw the children of the ScrollPane, which includes the buttons
        super.draw(batch, parentAlpha);

        // Restore the original clip
        ScissorStack.popScissors();
    }
}

