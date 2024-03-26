package edu.com55.burnstd.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import edu.comp55.burnstd.map.GameUI;
import edu.comp55.burnstd.map.Level;
import edu.comp55.burnstd.map.Tile;
import edu.comp55.burnstd.towers.Tower;

public class DraggingButton extends ImageButton {

	protected Level level;
	protected float startX;
	protected float startY;
	protected float positionX;
	protected float positionY;
	protected GameUI gameui;

	protected Tower tower;

	public DraggingButton(float x, float y, Texture texture, final Level level, final GameUI gameui) {
		super(new TextureRegionDrawable(new TextureRegion(texture)));
		this.gameui = gameui;
		this.level = level;
		this.setPosition(x, y);
		this.startX = this.getX();
		this.startY = this.getY();

		this.addListener(new ClickListener() {

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				//checks for out-of-bounds condition 
				boolean validTile = false;
				for (Tile t : level.getMap().getTiles()) {
					if (t.getBoundingRectangle().contains(positionX, positionY) && !t.isOccupied()) {
						validTile = true;
					}
				}
				System.out.println(validTile);
				if (validTile == true) {
					System.out.println("test");
					clickUp(event, x, y, pointer, button);
				}
				//check for isOccupied status
				
				
				gameui.getTowerButtons().add(DraggingButton.this).row();
				System.out.println("touchupDraggingButton");
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				clickDown(event, x, y, pointer, button);
				setStage(level.getGameScreen().getStage());
				level.getGameScreen().getStage().addActor(DraggingButton.this);
				System.out.println("touchdownDraggingButton");
				return true;
			}
		});
		
		this.addListener(new DragListener() {
			@Override
			public void drag(InputEvent event, float x, float y, int pointer) {
				moveBy(x - getWidth() / 2, y - getHeight() / 2);
				positionX = getX() + getWidth() / 2;
				positionY = getY() + getHeight() / 2;

			}
		});
	}

	public void clickDown(InputEvent event, float x, float y, int pointer, int button) {
		 setPosition(Gdx.input.getX() - getWidth()/2, Gdx.graphics.getHeight() - Gdx.input.getY() - getHeight()/2);
		 level.getGameScreen().getUiStage().setScrollFocus(null);
	}

	public void clickUp(InputEvent event, float x, float y, int pointer, int button) {
	}
}
