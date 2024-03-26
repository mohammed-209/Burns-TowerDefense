package edu.comp55.burnstd.screens;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import edu.comp55.burnstd.map.Level;

public class LoseScreen {

	BitmapFont scoreFont;

	public LoseScreen(final Level level) {
		Skin skin = new Skin(Gdx.files.internal("skins/testskin/test.json"));
		final Window loseWindow = new Window ("haha loser", skin);
		loseWindow.setMovable(false);
		scoreFont = new BitmapFont(Gdx.files.internal("fonts/main.fnt"));
		
//		TextButton restartButton = new TextButton("continue", skin);
//		restartButton.addListener(new ClickListener() {
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//				System.out.println("restart pressed");
//				loseWindow.setVisible(false);
//				level.setIsPaused(false);
//				((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(Gdx.files.internal("level/level1.json")));
//
//			}
//		});

		// Add score to window using scoreFont




		TextButton exitButton = new TextButton("exit", skin);
		exitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				System.out.println("exit pressed");
				((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
			}
		});
		
		//loseWindow.add(restartButton).row();;
		loseWindow.add(exitButton).row();
		loseWindow.add("Score: " + level.getScore()).row();
		// Add score to global score
		UpgradeScreen.score += level.getScore();

		loseWindow.setSize(level.getGameScreen().getStage().getWidth()/1.5f, level.getGameScreen().getStage().getHeight()/1.5f);
		loseWindow.setPosition(level.getGameScreen().getStage().getWidth()/2 - loseWindow.getWidth()/2, level.getGameScreen().getStage().getHeight()/2 - loseWindow.getHeight()/2);
		level.getGameScreen().getStage().addActor(loseWindow);
		
	}

}
