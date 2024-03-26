package edu.comp55.burnstd.screens;

import java.awt.*;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import edu.comp55.burnstd.enemy.Enemy;
import edu.comp55.burnstd.map.Level;
import edu.comp55.burnstd.map.Map;
import edu.comp55.burnstd.map.Path;
import edu.comp55.burnstd.towers.PlayerBase;
import edu.comp55.burnstd.towers.Tower;

public class GameScreen implements Screen, InputProcessor {

	private InputMultiplexer inputMultiplexer;
	private OrthographicCamera camera;
	private Viewport viewport;
	private SpriteBatch batch;
	private Stage stage;
	private ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
	private Level level;
	JsonValue levelData;
	private BitmapFont moneyFont;
	private BitmapFont burnsTowerHealth;

	private Texture uiBackgroundTexture;
	private Sprite uiBackgroundSprite;
	
	private Texture uiMoneyThingTexture;
	private Sprite uiMoneyThingSprite;

	private TextButton waveButton;

	// Testing towerButton
	private TextButton towerButton;

	private boolean debug = false;

	private Skin pauseSkin = new Skin(Gdx.files.internal("skins/testskin/test.json"));

	private float deltaTimer = 0f; // TEMP VAR FOR TESTING
	private float speedMultiplier;

	LoseScreen loseScreen;
	private boolean lost;
	private boolean won;

	public GameScreen(FileHandle level) {
		levelData = new JsonReader().parse(level);
	}

	@Override
	public void show() {
		inputMultiplexer = new InputMultiplexer();
		Gdx.input.setInputProcessor(inputMultiplexer);
		speedMultiplier = 1f;
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		viewport = new FitViewport(800, 600, camera);
		stage = new Stage(viewport);
		level = new Level(levelData, this);

		// Add input processors to the multiplexer
		
		inputMultiplexer.addProcessor(level);
		inputMultiplexer.addProcessor(stage);


		uiBackgroundTexture = new Texture("textures/screencomponents/UI/uibackground.png");
		uiBackgroundSprite = new Sprite(uiBackgroundTexture);
		uiBackgroundSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		
		moneyFont = new BitmapFont(Gdx.files.internal("fonts/main.fnt"));
		burnsTowerHealth = new BitmapFont(Gdx.files.internal("fonts/main.fnt"));  
		
		
		uiMoneyThingTexture = new Texture("textures/screencomponents/UI/money_thing.png");

		uiMoneyThingSprite = new Sprite(uiMoneyThingTexture);

		uiMoneyThingSprite.setPosition(0, Gdx.graphics.getHeight() - uiMoneyThingSprite.getHeight());
		buildPauseButton();


	}

	public Stage getStage() {
		return stage;
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	@Override
	public void render(float delta) {
		//Doesn't update if its paused
		if (level.getIsPaused()) {
			delta = 0;
		}

		delta = delta * speedMultiplier;
		
		deltaTimer += delta;
		ScreenUtils.clear(255, 255, 255, 1);

		level.update(delta);


		batch.begin();
		level.draw(batch);
		batch.end();

		level.getMap().drawMap();
		for (Tower t : level.getMap().getTowers()) {
			t.drawRange();
		}




		batch.begin();

		for (Path p : level.getMap().getPath()) {
			p.draw(batch);
		}

		// builds the path based on the keys pressed.
		if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
			System.out.println("left pressed");
			level.getMap().buildNewPathTile("left");
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
			System.out.println("right pressed");
			level.getMap().buildNewPathTile("right");// 1 is code of key pressed right
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			System.out.println("up pressed");
			level.getMap().buildNewPathTile("up");// 2 is code of key pressed up
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			System.out.println("downpressed");
			level.getMap().buildNewPathTile("down");// 3 is code of key pressed down
		}

		for (Enemy e : level.getMap().getEnemyList()) {
			e.update(delta);
			e.draw(batch);
		}

		for (Tower t : level.getMap().getTowers()) {
			t.draw(batch);
			t.update(delta);

		}

		checkWinOrLoss();

		uiBackgroundSprite.draw(batch);
		uiMoneyThingSprite.draw(batch);

		moneyFont.draw(batch, "$" + String.valueOf(level.getMoney()), 10, 585);

		//health string the screen.
		burnsTowerHealth.draw(batch, "Health: " + String.valueOf(level.getMap().getPlayerBase().getHealth()), 400, 585);

		batch.end();

		stage.act(delta);
		stage.setDebugAll(debug);
		stage.draw();

		level.getCurrMode().act(delta);
		level.getCurrMode().draw();
		level.getCurrMode().setDebugAll(debug);

	}
	
	public void buildPauseButton() {

		Button pauseButton = new Button(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textures/screencomponents/UI/pauseButton.png")))));
		pauseButton.setSize(50, 50);
		pauseButton.setPosition(level.getGameScreen().getViewport().getWorldWidth()/3, level.getGameScreen().getViewport().getWorldHeight()-pauseButton.getHeight());
		pauseButton.addListener(new ClickListener() {
		@Override
			public void clicked(InputEvent event, float x, float y) {
			System.out.println("pause clicked");

				
				PauseScreen pauseScreen = new PauseScreen(pauseSkin , level);
				level.setIsPaused(true);


				
			}
		});

		stage.addActor(pauseButton);
	}

	public void setLost(boolean lost) {
		this.lost = lost;
	}

	public void setWon(boolean won) {
		this.won = won;
	}

	public boolean getWon() {
		return won;
	}

	public boolean getLost() {
		return lost;
	}

	public void checkWinOrLoss() {
		if (won){
			level.setIsPaused(true);
			if (loseScreen == null) {
				loseScreen = new LoseScreen(level);
			}
		}


		if (lost) {
			level.setIsPaused(true);
			if (loseScreen == null) {
				loseScreen = new LoseScreen(level);
			}
		}
	}



	

	public Viewport getViewport() {
		return viewport;
	}

	public Stage getUiStage() {
		return stage;
	}


	public InputMultiplexer getInputMultiplexer() {
		return inputMultiplexer;
	}


	public void setSpeedMultiplier(float speedMultiplier) {
		this.speedMultiplier = speedMultiplier;
	}


	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		dispose();

	}

	@Override
	public void dispose() {
		level.getMusic().dispose();

	}

	@Override
	public boolean keyDown(int keycode) {

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		System.out.println("Key up");
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchCancelled(int a, int b, int c, int d){
		return false;
	}

}
