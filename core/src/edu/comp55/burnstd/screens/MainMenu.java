package edu.comp55.burnstd.screens;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MainMenu implements Screen, InputProcessor {

	private Camera camera;

	// A stage is required for screens that will have functionality
	private Stage stage;

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	// Table is used to align UI components
	private Table table;

	// Skin is a predefined look for buttons made in a separate tool
	private Skin skin;

	private static boolean firstTime = true;

	// SpriteBatch defs
	private SpriteBatch batch;

	// Texture defs
	private Texture bgTexture;
	private Texture logoTexture;
	private Texture leafTexture;

	// Sprite defs
	private Sprite bgSprite;
	private Sprite logoSprite;

	// ArrayList of leaf sprites to iterate through later
	private ArrayList<Sprite> leaves = new ArrayList<Sprite>();

	// Leaf timer for spawning leaves
	private float leafSpawnTimer;

	// Music defs
	private Music menuMusic = Gdx.audio.newMusic(Gdx.files.internal("music/mainmenu.ogg"));

	// SFX defs
	private Sound thunder = Gdx.audio.newSound(Gdx.files.internal("sounds/lightning.ogg"));

	float fadeTimer;
	private Texture whiteTexture;
	private Sprite whiteSprite;
	
	//adding skin for levels menu
	private Skin levelsSkin = new Skin(Gdx.files.internal("skins/testskin/test.json"));

	private float opacity;

	@Override
	public void show() {
		stage = new Stage(new FitViewport(800, 600));
		table = new Table();
		batch = new SpriteBatch();
		skin = new Skin(Gdx.files.internal("skins/testskin/test.json"));
		leafSpawnTimer = 0f;

		fadeTimer = 0f;

		// Define textures
		logoTexture = new Texture("textures/mainmenu/logo.png");
		leafTexture = new Texture(Gdx.files.internal("textures/mainmenu/leaf.png"));
		whiteTexture = new Texture("textures/mainmenu/white.png");

		// Define sprites
		logoSprite = new Sprite(logoTexture);

		// Creating buttons for the menu
		TextButton playButton = new TextButton("Play", skin);
		TextButton optionsButton = new TextButton("Settings", skin);
		TextButton quitButton = new TextButton("Quit", skin);
		//testing challenge menu 
		TextButton levelsButton = new TextButton("Challenge levels", skin);

		TextButton upgradesButton = new TextButton("Upgrades", skin);

		// Tell the program to send inputs to this screen
		Gdx.input.setInputProcessor(stage);

		// Tells the UI table to fill the entire screen and adds the table to the stage
		table.setFillParent(true);
		stage.addActor(table);

		// Background setup
		bgTexture = new Texture(Gdx.files.internal("textures/mainmenu/bg.jpg"));
		bgSprite = new Sprite(bgTexture);
		bgSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		// Play thunder sound when the cutscene finishes
		if (firstTime) {
			thunder.play(OptionsScreen.soundVolume);

		}
		else {
			opacity = 0f;
		}
		firstTime = false;

		// Play the main menu music
		menuMusic.setVolume(OptionsScreen.musicVolume);
		menuMusic.play();

		Image logo = new Image(new SpriteDrawable(logoSprite));

		// Put the buttons into the table
		table.setX(150);
		table.defaults().pad(10f);
		table.add(logo).fillX().uniformX();
		table.row();
		table.add(playButton).fillX().uniformX();
		table.row();
		//adding challenge levels button
		table.add(levelsButton).fillX().uniformX();
		table.row();
		table.add(upgradesButton).fillX().uniformX();
		table.row();
		table.add(optionsButton).fillX().uniformX();
		table.row();
		table.add(quitButton).fillX().uniformX();

		// Play button click handler
		playButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// Code to run when button is pressed
				System.out.println("Play");
				((Game) Gdx.app.getApplicationListener())
						.setScreen(new GameScreen(Gdx.files.internal("level/level1.json")));
			};
		});
		// Options button click handler
		optionsButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// Code to run when button is pressed
				OptionsScreen options = new OptionsScreen(stage);
				stage.addActor(options);

			}
		});
		// Quit button click handler
		quitButton.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// Code to run when button is pressed
				System.out.println("Quit");
				Gdx.app.exit();
			}
		});
		//levels button click handler
		levelsButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				LevelsScreen levelsScreen = new LevelsScreen(skin, MainMenu.this);
			}
		});
		//upgrades button click handler
		upgradesButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new UpgradeScreen());
			}
		});
	}

	@Override
	public void render(float delta) {
		// Clears the previous frame

		fadeTimer += delta;

		opacity = 1f - MathUtils.clamp(fadeTimer / 0.5f, 0f, 1f);

		ScreenUtils.clear(0, 0, 0, 1);

		leafSpawnTimer += delta;

		menuMusic.setVolume(OptionsScreen.musicVolume);

		// Spawns leafs after a certain amount of time (delta) passes
		if (leafSpawnTimer >= 0.3f) {
			leafSpawnTimer = 0f;
			float spawnX, spawnY;
			if (MathUtils.randomBoolean()) {
				// Spawn on top edge
				spawnX = MathUtils.random(0, Gdx.graphics.getWidth());
				spawnY = Gdx.graphics.getHeight();
			} else {
				// Spawn on right edge
				spawnX = Gdx.graphics.getWidth();
				spawnY = MathUtils.random(0, Gdx.graphics.getHeight());
			}
			Sprite leaf = new Sprite(leafTexture);
			leaf.setPosition(spawnX, spawnY);
			leaves.add(leaf);
		}

		// Move all leaves down and left and removes leaves that move out of bounds
		if (leaves != null) {
			// Crazy iterator madness to avoid removing an object in the arraylist we're
			// iterating over causing a scary concurrentmodification error
			for (Iterator<Sprite> it = leaves.iterator(); it.hasNext();) {
				Sprite leaf = it.next();
				// Move each leaf down and left each frame
				leaf.translate(-50 * Gdx.graphics.getDeltaTime(), -50 * Gdx.graphics.getDeltaTime());
				if (leaf.getY() < 0 || leaf.getX() < 0) {
					it.remove();
				}
			}
		}

		stage.act(delta);

		// Draws the screen
		batch.begin();
		bgSprite.draw(batch);

		for (Sprite leaf : leaves) {
			leaf.draw(batch);
		}
		batch.end();

		stage.draw();

		// Fade in from white
		batch.begin();
		batch.setColor(1f, 1f, 1f, opacity);
		batch.draw(whiteTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();

	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
		batch.dispose();
		menuMusic.dispose();
		bgTexture.dispose();
		thunder.dispose();
		logoTexture.dispose();
		leafTexture.dispose();
	}

	@Override
	public boolean keyUp(int keycode) {
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
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchCancelled(int a, int b, int c, int d){
		return false;
	}

}
